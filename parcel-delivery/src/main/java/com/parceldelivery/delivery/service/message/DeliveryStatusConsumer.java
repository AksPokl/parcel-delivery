package com.parceldelivery.delivery.service.message;

import com.parceldelivery.delivery.model.message.ChangeDeliveryStatusMessageRequest;
import com.parceldelivery.delivery.service.DeliveryService;
import com.parceldelivery.delivery.service.StatusProcessingHelper;
import com.parceldelivery.message.consumer.Consumer;
import com.parceldelivery.message.model.UpdateDeliveryStatusMessage;
import com.parceldelivery.message.service.MessageMetadataService;
import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class DeliveryStatusConsumer extends Consumer<UpdateDeliveryStatusMessage> {

    private final StatusProcessingHelper statusProcessingHelper;
    private final DeliveryService deliveryService;

    public DeliveryStatusConsumer(MessageMetadataService messageMetadataService,
                                  StatusProcessingHelper statusProcessingHelper,
                                  DeliveryService deliveryService) {
        super(messageMetadataService);
        this.statusProcessingHelper = statusProcessingHelper;
        this.deliveryService = deliveryService;
    }

    @Override
    public void processMessage(@NonNull UpdateDeliveryStatusMessage message) {
        log.info("Received message with id " + message.getId());
        DeliveryStatus deliveryStatus = statusProcessingHelper.processStatus(CourierStatus.valueOf(message.getStatus()));
        try {
            deliveryService.changeStatus(ChangeDeliveryStatusMessageRequest.builder()
                    .status(deliveryStatus)
                    .deliveryId(UUID.fromString(message.getDeliveryId()))
                    .courierId(UUID.fromString(message.getCourierId()))
                    .build());
        } catch (Exception ex) {
            log.warn("No possible to change status for deliveryId={}", message.getDeliveryId());
        }
    }
}
