package com.parceldelivery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ParcelDeliveryDetailResponse {

    private final UUID parcelId;
    private final UUID deliveryId;
    private final String status;
    private final LocalDate deliveryDate;
    private final String country;
    private final String city;
    private final String address;
}
