package com.parceldelivery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateParcelResponse {

    private final String parcelId;
    private final String deliveryId;
}
