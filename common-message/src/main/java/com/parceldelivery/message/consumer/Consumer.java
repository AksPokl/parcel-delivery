package com.parceldelivery.message.consumer;

import com.parceldelivery.message.model.CreateMessageMetadataRequest;
import com.parceldelivery.message.model.Message;
import com.parceldelivery.message.service.MessageMetadataService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
@AllArgsConstructor
public abstract class Consumer<T extends Message> {

    private final MessageMetadataService messageMetadataService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(@NonNull T message,
                                Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("Received message with id " + message.getId());
        boolean isMessageSent = messageMetadataService.isMessageAlreadySent(message.getId());
        try {
            if (isMessageSent) {
                channel.basicNack(tag, false, false);
            } else {
                processMessage(message);
                messageMetadataService.saveMessage(CreateMessageMetadataRequest.builder()
                        .id(message.getId())
                        .tag(tag)
                        .build());
                channel.basicAck(tag, false);
            }
        } catch (Exception ex) {
            log.error("Error happened during receiving a message", ex);
        }
    }

    public abstract void processMessage(T message);
}
