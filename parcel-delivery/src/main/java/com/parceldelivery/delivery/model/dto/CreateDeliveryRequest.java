package com.parceldelivery.delivery.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateDeliveryRequest {

    private final String country;

    private final String city;

    private final String address;

    private final UUID parcelId;
}
