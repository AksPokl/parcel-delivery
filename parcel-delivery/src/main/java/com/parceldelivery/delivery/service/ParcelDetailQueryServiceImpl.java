package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.exception.NotFoundException;
import com.parceldelivery.delivery.mapper.ParcelDeliveryDetailMapper;
import com.parceldelivery.delivery.model.entity.DeliveryEntity;
import com.parceldelivery.delivery.model.entity.ParcelEntity;
import com.parceldelivery.delivery.repository.DeliveryDetailRepository;
import com.parceldelivery.delivery.repository.ParcelDetailRepository;
import com.parceldelivery.delivery.security.TokenHelper;
import com.parceldelivery.model.response.DeliveryDetailResponse;
import com.parceldelivery.model.response.ParcelDeliveryDetailResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParcelDetailQueryServiceImpl implements ParcelDetailQueryService {

    private final ParcelDetailRepository parcelDetailRepository;
    private final DeliveryDetailRepository deliveryDetailRepository;
    private final ParcelDeliveryDetailMapper parcelDeliveryDetailMapper;
    private final TokenHelper tokenHelper;

    @Override
    public ParcelDeliveryDetailResponse getUserParcelDeliveryDetail(@NonNull UUID parcelId) {
        ParcelEntity parcel = parcelDetailRepository.findById(parcelId)
                .orElseThrow(() -> new NotFoundException("parcel", parcelId.toString()));
        return parcelDeliveryDetailMapper.toParcelDeliveryDetailResponse(parcel);
    }

    @Override
    public List<ParcelDeliveryDetailResponse> getUserParcelDeliveryDetails(@NonNull String token, @NonNull Pageable pageable) {
        return parcelDetailRepository.findAllByUserId(tokenHelper.getUserId(token), pageable).stream()
                .map(parcelDeliveryDetailMapper::toParcelDeliveryDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDetailResponse getDeliveryDetail(@NonNull UUID deliveryId) {
        DeliveryEntity delivery = deliveryDetailRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("delivery", deliveryId.toString()));
        return parcelDeliveryDetailMapper.toDeliveryDetailResponse(delivery);
    }

    @Override
    public List<ParcelDeliveryDetailResponse> getCourierDeliveryDetails(@NonNull String token, @NonNull Pageable pageable) {
        return deliveryDetailRepository.findAllByCourierId(tokenHelper.getUserId(token), pageable).stream()
                .map(parcelDeliveryDetailMapper::toParcelDeliveryDetailResponse)
                .collect(Collectors.toList());
    }
}
