package com.parceldelivery.courier.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AssignDeliveryRequest {

    private final UUID deliveryId;
    private final UUID courierId;
}
