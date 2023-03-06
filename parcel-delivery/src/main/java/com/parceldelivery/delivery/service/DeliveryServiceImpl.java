package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.exception.NotFoundException;
import com.parceldelivery.delivery.model.dto.CreateDeliveryRequest;
import com.parceldelivery.delivery.model.entity.DeliveryEntity;
import com.parceldelivery.delivery.model.message.ChangeDeliveryStatusMessageRequest;
import com.parceldelivery.delivery.repository.DeliveryRepository;
import com.parceldelivery.delivery.repository.ParcelRepository;
import com.parceldelivery.delivery.service.message.DeliveryStatusProducer;
import com.parceldelivery.model.request.ChangeDeliveryStatusApiRequest;
import com.parceldelivery.model.request.DeliveryStatus;
import com.parceldelivery.model.request.UpdateDestinationApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryStatusService deliveryStatusService;
    private final DeliveryRepository deliveryRepository;
    private final ParcelRepository parcelRepository;
    private final DeliveryStatusProducer deliveryStatusProducer;
    private final StatusProcessingHelper statusProcessingHelper;

    @Transactional
    @Override
    public CreateEntityResponse createDelivery(@NonNull CreateDeliveryRequest request) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setId(UUID.randomUUID());
        deliveryEntity.setCity(request.getCity());
        deliveryEntity.setCountry(request.getCountry());
        deliveryEntity.setAddress(request.getAddress());
        deliveryEntity.setCreatedAt(LocalDateTime.now());
        deliveryEntity.setLastStatus(DeliveryStatus.CREATED.name());
        deliveryEntity.setParcel(parcelRepository.getReferenceById(request.getParcelId()));
        DeliveryEntity createdDelivery = deliveryRepository.save(deliveryEntity);
        deliveryStatusService.createStatus(ChangeDeliveryStatusApiRequest.builder()
                .status(DeliveryStatus.CREATED)
                .build(), createdDelivery.getId());
        return CreateEntityResponse.builder()
                .id(createdDelivery.getId())
                .build();
    }

    @Transactional
    @Override
    public void updateDestination(@NonNull UpdateDestinationApiRequest request, @NonNull UUID deliveryId) {
        DeliveryEntity deliveryEntity = getDeliveryEntity(deliveryId);
        if (statusProcessingHelper.canUpdateDestination(DeliveryStatus.valueOf(deliveryEntity.getLastStatus()))) {
            log.info("Destination with deliveryId={} can be updated. Updating.", deliveryId);
            updateDestination(request, deliveryEntity);
        } else {
            log.info("Destination with deliveryId={} can not be updated. Skip.", deliveryId);
        }
    }

    @Transactional
    @Override
    public void cancelDelivery(@NonNull UUID deliveryId) {
        changeStatus(deliveryId, Optional.empty(), DeliveryStatus.CANCELLED);
    }

    @Transactional
    @Override
    public void changeStatus(@NonNull ChangeDeliveryStatusApiRequest request, @NonNull UUID deliveryId) {
        changeStatus(deliveryId, Optional.empty(), request.getStatus());
    }

    @Override
    public void changeStatus(ChangeDeliveryStatusMessageRequest request) {
        changeStatus(request.getDeliveryId(), Optional.of(request.getCourierId()), request.getStatus());
    }

    private void changeStatus(UUID deliveryId, Optional<UUID> courierId, DeliveryStatus status) {
        DeliveryEntity deliveryEntity = getDeliveryEntity(deliveryId);
        if (DeliveryStatus.valueOf(deliveryEntity.getLastStatus()) == status) {
            log.info("Delivery is with the same status. Skip updating.");
            return;
        }
        deliveryEntity.setLastStatus(status.name());
        courierId.ifPresent(deliveryEntity::setCourierId);
        deliveryRepository.save(deliveryEntity);
        deliveryStatusService.createStatus(ChangeDeliveryStatusApiRequest.builder()
                .status(status)
                .build(), deliveryId);
        if (statusProcessingHelper.canSendMessage(status)) {
            log.info("Sending CANCEL status to courier for deliveryId={}", deliveryId);
            deliveryStatusProducer.sendMessage(ChangeDeliveryStatusMessageRequest.builder()
                    .status(status)
                    .deliveryId(deliveryId)
                    .build());
        }
    }

    private DeliveryEntity getDeliveryEntity(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("delivery", deliveryId.toString()));
    }

    private void updateDestination(UpdateDestinationApiRequest request, DeliveryEntity deliveryEntity) {
        deliveryEntity.setCity(request.getCity() == null ? deliveryEntity.getCity() : request.getCity());
        deliveryEntity.setCountry(request.getCountry() == null ? deliveryEntity.getCountry() : request.getCountry());
        deliveryEntity.setLastStatus(DeliveryStatus.DESTINATION_UPDATED.name());
        deliveryRepository.save(deliveryEntity);

        deliveryStatusService.createStatus(ChangeDeliveryStatusApiRequest.builder()
                .status(DeliveryStatus.DESTINATION_UPDATED)
                .build(), deliveryEntity.getId());
    }
}
