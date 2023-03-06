package com.parceldelivery.courier.service.message;

import com.parceldelivery.courier.model.message.ChangeCourierStatusMessageRequest;
import com.parceldelivery.courier.service.CourierService;
import com.parceldelivery.courier.service.StatusProcessingHelper;
import com.parceldelivery.message.consumer.Consumer;
import com.parceldelivery.message.model.UpdateCourierStatusMessage;
import com.parceldelivery.message.service.MessageMetadataService;
import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CourierStatusConsumer extends Consumer<UpdateCourierStatusMessage> {

    private final StatusProcessingHelper statusProcessingHelper;
    private final CourierService courierService;

    public CourierStatusConsumer(MessageMetadataService messageMetadataService,
                                 StatusProcessingHelper statusProcessingHelper,
                                 CourierService courierService) {
        super(messageMetadataService);
        this.statusProcessingHelper = statusProcessingHelper;
        this.courierService = courierService;
    }

    @Override
    public void processMessage(@NonNull UpdateCourierStatusMessage message) {
        log.info("Received message with id " + message.getId());
        CourierStatus courierStatus = statusProcessingHelper.processStatus(DeliveryStatus.valueOf(message.getStatus()));
        try {
            courierService.changeStatus(ChangeCourierStatusMessageRequest.builder()
                    .status(courierStatus)
                    .deliveryId(UUID.fromString(message.getDeliveryId()))
                    .build());
        } catch (Exception ex) {
            log.warn("No possible to change status for deliveryId={}", message.getDeliveryId());
        }
    }
}
