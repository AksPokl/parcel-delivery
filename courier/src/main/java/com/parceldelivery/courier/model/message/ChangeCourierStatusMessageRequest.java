package com.parceldelivery.courier.model.message;

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
public class ChangeCourierStatusMessageRequest {

    private UUID deliveryId;
    private UUID courierId;
    private CourierStatus status;
}
