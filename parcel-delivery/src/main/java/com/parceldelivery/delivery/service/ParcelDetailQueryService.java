package com.parceldelivery.delivery.service;

import com.parceldelivery.model.response.DeliveryDetailResponse;
import com.parceldelivery.model.response.ParcelDeliveryDetailResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParcelDetailQueryService {

    ParcelDeliveryDetailResponse getUserParcelDeliveryDetail(UUID parcelId);

    List<ParcelDeliveryDetailResponse> getUserParcelDeliveryDetails(String token, Pageable pageable);

    DeliveryDetailResponse getDeliveryDetail(UUID deliveryId);

    List<ParcelDeliveryDetailResponse> getCourierDeliveryDetails(String token, Pageable pageable);
}
