package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.model.entity.DeliveryStatusEntity;
import com.parceldelivery.delivery.repository.DeliveryRepository;
import com.parceldelivery.delivery.repository.DeliveryStatusRepository;
import com.parceldelivery.model.request.ChangeDeliveryStatusApiRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusRepository deliveryStatusRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    @Override
    public void createStatus(@NonNull ChangeDeliveryStatusApiRequest request, @NonNull UUID deliveryId) {
        DeliveryStatusEntity deliveryStatusEntity = new DeliveryStatusEntity();
        deliveryStatusEntity.setStatus(request.getStatus().name());
        deliveryStatusEntity.setCreatedAt(LocalDateTime.now());
        deliveryStatusEntity.setDelivery(deliveryRepository.getReferenceById(deliveryId));
        deliveryStatusRepository.save(deliveryStatusEntity);
    }
}
