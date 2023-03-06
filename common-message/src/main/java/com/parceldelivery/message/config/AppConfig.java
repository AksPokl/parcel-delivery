package com.parceldelivery.message.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableRabbit
@Configuration
@ComponentScan("com.parceldelivery.message.*")
@EntityScan("com.parceldelivery.message.model")
@EnableJpaRepositories("com.parceldelivery.message.repository")
public class AppConfig {

}
