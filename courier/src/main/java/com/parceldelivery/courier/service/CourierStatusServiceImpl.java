package com.parceldelivery.courier.service;

import com.parceldelivery.courier.model.dto.ChangeCourierStatusRequest;
import com.parceldelivery.courier.model.entity.CourierStatusEntity;
import com.parceldelivery.courier.repository.CourierRepository;
import com.parceldelivery.courier.repository.CourierStatusRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourierStatusServiceImpl implements CourierStatusService {

    private final CourierStatusRepository courierStatusRepository;
    private final CourierRepository courierRepository;

    @Override
    public void changeStatus(@NonNull ChangeCourierStatusRequest request, @NonNull UUID courierId) {
        CourierStatusEntity entity = new CourierStatusEntity();
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(request.getStatus().name());
        entity.setDeliveryId(request.getDeliveryId());
        entity.setCourier(courierRepository.getReferenceById(courierId));
        courierStatusRepository.save(entity);
    }
}
