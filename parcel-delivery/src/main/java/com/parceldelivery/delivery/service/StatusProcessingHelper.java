package com.parceldelivery.delivery.service;

import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StatusProcessingHelper {

    public DeliveryStatus processStatus(@NonNull CourierStatus status) {
        if (status == CourierStatus.ASSIGNED) {
            return DeliveryStatus.COURIER_ASSIGNED;
        } else if (status == CourierStatus.CANCELLED) {
            return DeliveryStatus.CANCELLED;
        } else if (status == CourierStatus.PICKED_UP) {
            return DeliveryStatus.IN_DELIVERY;
        } else if (status == CourierStatus.DELIVERED || status == CourierStatus.READY_FOR_NEW_ORDER) {
            return DeliveryStatus.DELIVERED;
        }

        return DeliveryStatus.UNKNOWN;
    }

    public boolean canSendMessage(@NonNull DeliveryStatus status) {
        return status == DeliveryStatus.CANCELLED ||
                status == DeliveryStatus.DESTINATION_UPDATED;
    }

    public boolean canUpdateDestination(@NonNull DeliveryStatus status) {
        return status != DeliveryStatus.CANCELLED &&
                status != DeliveryStatus.DELIVERED;
    }
}
