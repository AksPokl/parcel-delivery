package com.parceldelivery.delivery.mapper;

import com.parceldelivery.delivery.model.entity.DeliveryEntity;
import com.parceldelivery.delivery.model.entity.ParcelEntity;
import com.parceldelivery.model.response.DeliveryDetailResponse;
import com.parceldelivery.model.response.ParcelDeliveryDetailResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ParcelDeliveryDetailMapper {

    public ParcelDeliveryDetailResponse toParcelDeliveryDetailResponse(@NonNull ParcelEntity parcel) {
        DeliveryEntity delivery = parcel.getDelivery();
        return ParcelDeliveryDetailResponse.builder()
                .deliveryId(delivery.getId())
                .address(delivery.getAddress())
                .city(delivery.getCity())
                .country(delivery.getCountry())
                .status(delivery.getLastStatus())
                .parcelId(parcel.getId())
                .deliveryDate(parcel.getDeliveryDate())
                .build();
    }

    public ParcelDeliveryDetailResponse toParcelDeliveryDetailResponse(@NonNull DeliveryEntity delivery) {
        ParcelEntity parcel = delivery.getParcel();
        return ParcelDeliveryDetailResponse.builder()
                .deliveryId(delivery.getId())
                .address(delivery.getAddress())
                .city(delivery.getCity())
                .country(delivery.getCountry())
                .status(delivery.getLastStatus())
                .parcelId(parcel.getId())
                .deliveryDate(parcel.getDeliveryDate())
                .build();
    }

    public DeliveryDetailResponse toDeliveryDetailResponse(@NonNull DeliveryEntity delivery) {
        return DeliveryDetailResponse.builder()
                .status(delivery.getLastStatus())
                .courierId(delivery.getCourierId())
                .build();
    }
}
