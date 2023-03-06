package com.parceldelivery.courier.service;

import com.parceldelivery.courier.client.ParcelDeliveryClient;
import com.parceldelivery.courier.exception.NotFoundException;
import com.parceldelivery.courier.model.dto.AssignDeliveryRequest;
import com.parceldelivery.courier.model.dto.ChangeCourierStatusRequest;
import com.parceldelivery.courier.model.entity.CourierEntity;
import com.parceldelivery.courier.model.message.ChangeCourierStatusMessageRequest;
import com.parceldelivery.courier.repository.CourierRepository;
import com.parceldelivery.courier.service.message.CourierStatusProducer;
import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import com.parceldelivery.model.response.DeliveryDetailResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CourierDeliveryServiceImpl implements CourierDeliveryService {

    private final ParcelDeliveryClient parcelDeliveryClient;
    private final CourierRepository courierRepository;
    private final CourierStatusService courierStatusService;
    private final StatusProcessingHelper statusProcessingHelper;
    private final CourierStatusProducer courierStatusProducer;

    @Transactional
    @Override
    public void assignDelivery(@NonNull AssignDeliveryRequest request, @NonNull String token) {
        UUID deliveryId = request.getDeliveryId();
        UUID courierId = request.getCourierId();
        DeliveryDetailResponse response = parcelDeliveryClient.getDeliveryDetails(deliveryId, token);
        validateResponse(response);
        CourierEntity courier = courierRepository.findById(request.getCourierId())
                .orElseThrow(() -> new NotFoundException("courier", "courierId", courierId.toString()));
        if (courier.getCurrentDeliveryId() != null) {
            throw new IllegalStateException("Courier is already assigned to delivery.");
        }
        courier.setCurrentDeliveryId(request.getDeliveryId());
        courier.setCurrentStatus(CourierStatus.ASSIGNED.name());
        courierRepository.save(courier);
        courierStatusService.changeStatus(ChangeCourierStatusRequest.builder()
                .status(CourierStatus.ASSIGNED)
                .deliveryId(deliveryId)
                .build(), courierId);
        if (statusProcessingHelper.canSendAssignMessage(CourierStatus.ASSIGNED)) {
            courierStatusProducer.sendMessage(ChangeCourierStatusMessageRequest.builder()
                    .courierId(courierId)
                    .deliveryId(deliveryId)
                    .status(CourierStatus.ASSIGNED)
                    .build());
        }
    }

    private void validateResponse(DeliveryDetailResponse response) {
        if (response == null) {
            log.warn("Empty response from parcel delivery client");
            return;
        }

        DeliveryStatus status = DeliveryStatus.valueOf(response.getStatus());
        if (status == DeliveryStatus.CANCELLED) {
            throw new IllegalStateException("Delivery is already cancelled. It's impossible to assign a courier");
        }
        if (response.getCourierId() != null) {
            throw new IllegalStateException("Delivery is already assigned to courier.");
        }
    }
}

