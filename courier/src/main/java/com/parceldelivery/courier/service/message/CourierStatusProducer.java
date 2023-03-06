package com.parceldelivery.courier.service.message;

import com.parceldelivery.courier.model.message.ChangeCourierStatusMessageRequest;
import com.parceldelivery.message.model.UpdateDeliveryStatusMessage;
import com.parceldelivery.message.producer.Producer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class CourierStatusProducer {

    private final Producer<UpdateDeliveryStatusMessage> producer;

    public void sendMessage(@NonNull ChangeCourierStatusMessageRequest request) {
        log.info("Sending a message with courier status={}, courierId={}", request.getStatus(), request.getCourierId());
        producer.sendMessage(toUpdateStatusMessage(request));
    }

    private UpdateDeliveryStatusMessage toUpdateStatusMessage(ChangeCourierStatusMessageRequest request) {
        return UpdateDeliveryStatusMessage.builder()
                .id(UUID.randomUUID())
                .courierId(String.valueOf(request.getCourierId()))
                .deliveryId(String.valueOf(request.getDeliveryId()))
                .status(String.valueOf(request.getStatus()))
                .build();
    }
}
