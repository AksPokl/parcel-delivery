package com.parceldelivery.courier.service;

import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StatusProcessingHelper {

    public CourierStatus processStatus(@NonNull DeliveryStatus status) {
        if (status == DeliveryStatus.CANCELLED) {
            return CourierStatus.CANCELLED;
        }

        return CourierStatus.UNKNOWN;
    }

    public boolean canSendAssignMessage(@NonNull CourierStatus courierStatus) {
        return courierStatus != CourierStatus.READY_FOR_NEW_ORDER;
    }
}
