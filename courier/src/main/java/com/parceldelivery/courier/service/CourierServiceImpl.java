package com.parceldelivery.courier.service;

import com.parceldelivery.courier.exception.NotFoundException;
import com.parceldelivery.courier.model.dto.ChangeCourierStatusRequest;
import com.parceldelivery.courier.model.dto.CreateCourierRequest;
import com.parceldelivery.courier.model.entity.CourierEntity;
import com.parceldelivery.courier.model.message.ChangeCourierStatusMessageRequest;
import com.parceldelivery.courier.repository.CourierRepository;
import com.parceldelivery.courier.security.TokenHelper;
import com.parceldelivery.courier.service.message.CourierStatusProducer;
import com.parceldelivery.model.request.ChangeCourierStatusApiRequest;
import com.parceldelivery.model.request.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierStatusService courierStatusService;
    private final TokenHelper tokenHelper;
    private final CourierStatusProducer courierStatusProducer;

    @Transactional
    @Override
    public void create(@NonNull CreateCourierRequest request) {
        CourierEntity createdCourier = courierRepository.save(toCourierEntity(request));
        courierStatusService.changeStatus(ChangeCourierStatusRequest.builder()
                .status(CourierStatus.CREATED)
                .build(), createdCourier.getId());
    }

    @Override
    public void changeStatus(@NonNull ChangeCourierStatusApiRequest request, @NonNull String token) {
        UUID courierId = tokenHelper.getUserId(token);
        CourierEntity courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NotFoundException("courier", "courierId", String.valueOf(request.getDeliveryId())));
        UUID deliveryId = request.getDeliveryId() == null ? null : UUID.fromString(request.getDeliveryId());
        changeStatus(courier, request.getStatus(), deliveryId);
        courierStatusProducer.sendMessage(ChangeCourierStatusMessageRequest.builder()
                .status(request.getStatus())
                .courierId(courierId)
                .deliveryId(deliveryId)
                .build());
    }

    @Override
    public void changeStatus(@NonNull ChangeCourierStatusMessageRequest request) {
        CourierEntity courier = courierRepository.findByCurrentDeliveryId(request.getDeliveryId())
                .orElseThrow(() -> new NotFoundException("courier", "deliveryId", String.valueOf(request.getDeliveryId())));
        changeStatus(courier, request.getStatus(), request.getDeliveryId());
    }

    private void changeStatus(CourierEntity courier, CourierStatus status, UUID deliveryId) {
        courier.setCurrentStatus(String.valueOf(status));
        if (status == CourierStatus.READY_FOR_NEW_ORDER) {
            courier.setCurrentDeliveryId(null);
        }
        courierRepository.save(courier);
        courierStatusService.changeStatus(ChangeCourierStatusRequest.builder()
                .status(status)
                .deliveryId(deliveryId)
                .build(), courier.getId());
    }

    private CourierEntity toCourierEntity(CreateCourierRequest request) {
        CourierEntity courier = new CourierEntity();
        courier.setId(request.getUserId());
        courier.setUsername(request.getUsername());
        courier.setCreatedAt(LocalDateTime.now());
        courier.setCurrentStatus(CourierStatus.CREATED.name());
        return courier;
    }
}
