package com.parceldelivery.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rabbitmq")
@Configuration
@Data
public class RabbitMQProperties {

    private String host;

    private String username;

    private String password;

    private String exchange;

    private String routingkey;

    private String routingkeytosend;

    private String queue;
}
