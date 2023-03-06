package com.parceldelivery.courier.model.dto;

import com.parceldelivery.model.request.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCourierStatusRequest {

    private CourierStatus status;
    private UUID deliveryId;
}
