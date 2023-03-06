package com.parceldelivery.delivery.model.message;

import com.parceldelivery.model.request.DeliveryStatus;
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
public class ChangeDeliveryStatusMessageRequest {

    private UUID deliveryId;
    private UUID courierId;
    private DeliveryStatus status;
}
