package com.parceldelivery.message.producer;

import com.parceldelivery.message.config.RabbitMQProperties;
import com.parceldelivery.message.model.Message;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class Producer<T extends Message> {

    private final RabbitMQProperties properties;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(@NonNull T message) {
        try {
            rabbitTemplate.convertAndSend(properties.getExchange(),
                    properties.getRoutingkeytosend(),
                    message);
        } catch (Exception e) {
            log.error("Message is not sent.", e);
        }
    }
}
