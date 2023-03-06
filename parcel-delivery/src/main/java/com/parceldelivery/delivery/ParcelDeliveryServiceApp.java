package com.parceldelivery.delivery;

import com.parceldelivery.message.annotation.EnableMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableMessaging
@EnableEurekaClient
@SpringBootApplication
public class ParcelDeliveryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ParcelDeliveryServiceApp.class, args);
    }
}
