package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.model.dto.CreateDeliveryRequest;
import com.parceldelivery.delivery.model.entity.ParcelEntity;
import com.parceldelivery.delivery.repository.ParcelRepository;
import com.parceldelivery.delivery.security.TokenHelper;
import com.parceldelivery.model.request.CreateParcelApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;
import com.parceldelivery.model.response.CreateParcelResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final TokenHelper tokenHelper;
    private final DeliveryService deliveryService;

    @Transactional
    @Override
    public CreateParcelResponse createParcel(@NonNull CreateParcelApiRequest createParcelRequest,
                                             @NonNull String token) {
        ParcelEntity parcelEntity = new ParcelEntity();
        parcelEntity.setId(UUID.randomUUID());
        parcelEntity.setCreatedAt(LocalDateTime.now());
        parcelEntity.setWeight(createParcelRequest.getWeight());
        parcelEntity.setUserId(tokenHelper.getUserId(token));
        parcelEntity.setDeliveryDate(createParcelRequest.getDeliveryDate());
        ParcelEntity createdParcel = parcelRepository.save(parcelEntity);
        CreateEntityResponse delivery = deliveryService.createDelivery(toCreateDeliveryRequest(createParcelRequest, createdParcel.getId()));
        return CreateParcelResponse.builder()
                .parcelId(String.valueOf(createdParcel.getId()))
                .deliveryId(String.valueOf(delivery.getId()))
                .build();
    }

    private CreateDeliveryRequest toCreateDeliveryRequest(CreateParcelApiRequest request, UUID parcelId) {
        return CreateDeliveryRequest.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .address(request.getAddress())
                .parcelId(parcelId)
                .build();
    }
}
