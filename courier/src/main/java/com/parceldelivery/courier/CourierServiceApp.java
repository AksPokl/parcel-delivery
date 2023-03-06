package com.parceldelivery.courier;

import com.parceldelivery.message.annotation.EnableMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableMessaging
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CourierServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(CourierServiceApp.class, args);
    }
}