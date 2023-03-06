package com.parceldelivery.delivery.service.message;

import com.parceldelivery.delivery.model.message.ChangeDeliveryStatusMessageRequest;
import com.parceldelivery.message.model.UpdateCourierStatusMessage;
import com.parceldelivery.message.producer.Producer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DeliveryStatusProducer {

    private final Producer<UpdateCourierStatusMessage> producer;

    public void sendMessage(@NonNull ChangeDeliveryStatusMessageRequest request) {
        producer.sendMessage(toUpdateCourierStatusMessage(request));
    }

    private UpdateCourierStatusMessage toUpdateCourierStatusMessage(ChangeDeliveryStatusMessageRequest request) {
        return UpdateCourierStatusMessage.builder()
                .id(UUID.randomUUID())
                .status(String.valueOf(request.getStatus()))
                .deliveryId(String.valueOf(request.getDeliveryId()))
                .build();
    }
}
