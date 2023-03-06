package com.parceldelivery.message.annotation;

import com.parceldelivery.message.config.AppConfig;
import com.parceldelivery.message.config.RabbitMQConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RabbitMQConfig.class, AppConfig.class})
public @interface EnableMessaging {
}
