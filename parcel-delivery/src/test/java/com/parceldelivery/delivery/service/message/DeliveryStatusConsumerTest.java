package com.parceldelivery.delivery.service.message;

import com.parceldelivery.delivery.model.message.ChangeDeliveryStatusMessageRequest;
import com.parceldelivery.delivery.service.DeliveryService;
import com.parceldelivery.delivery.service.StatusProcessingHelper;
import com.parceldelivery.message.model.UpdateDeliveryStatusMessage;
import com.parceldelivery.message.service.MessageMetadataService;
import com.parceldelivery.model.request.CourierStatus;
import com.parceldelivery.model.request.DeliveryStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryStatusConsumerTest {

    private static final UUID DELIVERY_ID = UUID.randomUUID();
    private static final UUID COURIER_ID = UUID.randomUUID();

    @Mock
    private StatusProcessingHelper statusProcessingHelper;
    @Mock
    private DeliveryService deliveryService;
    @Mock
    private MessageMetadataService messageMetadataService;

    @Mock
    private UpdateDeliveryStatusMessage message;
    @Mock
    private ChangeDeliveryStatusMessageRequest request;

    @InjectMocks
    private DeliveryStatusConsumer consumer;

    // region processMessage
    @Test
    public void test_processMessage_success() {
        // prepare
        when(message.getStatus()).thenReturn(CourierStatus.PICKED_UP.name());
        when(message.getDeliveryId()).thenReturn(String.valueOf(UUID.randomUUID()));
        when(message.getCourierId()).thenReturn(String.valueOf(UUID.randomUUID()));
        when(statusProcessingHelper.processStatus(CourierStatus.PICKED_UP)).thenReturn(DeliveryStatus.IN_DELIVERY);
        // execute
        consumer.processMessage(message);
        // validate
        verify(deliveryService).changeStatus(ChangeDeliveryStatusMessageRequest.builder()
                .status(DeliveryStatus.IN_DELIVERY)
                .deliveryId(UUID.fromString(message.getDeliveryId()))
                .courierId(UUID.fromString(message.getCourierId()))
                .build());
    }

    @Test
    public void test_processMessage_throwsException() {
        // prepare
        when(message.getStatus()).thenReturn(CourierStatus.PICKED_UP.name());
        when(message.getDeliveryId()).thenReturn(String.valueOf(DELIVERY_ID));
        when(message.getCourierId()).thenReturn(String.valueOf(COURIER_ID));
        when(statusProcessingHelper.processStatus(CourierStatus.PICKED_UP)).thenReturn(DeliveryStatus.IN_DELIVERY);
        when(request.getDeliveryId()).thenReturn(DELIVERY_ID);
        when(request.getCourierId()).thenReturn(COURIER_ID);
        when(request.getStatus()).thenReturn(DeliveryStatus.IN_DELIVERY);
        doThrow(new RuntimeException("error!")).when(deliveryService).changeStatus(request);
        // execute
        consumer.processMessage(message);
        // validate
        verify(deliveryService).changeStatus(ChangeDeliveryStatusMessageRequest.builder()
                .status(DeliveryStatus.IN_DELIVERY)
                .deliveryId(UUID.fromString(message.getDeliveryId()))
                .courierId(UUID.fromString(message.getCourierId()))
                .build());
    }
    // endregion
}
