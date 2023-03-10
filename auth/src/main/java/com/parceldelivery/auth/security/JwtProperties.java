package com.parceldelivery.auth.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@Configuration
@Data
public class JwtProperties {

    private String hmacSecret;
    private String subject;
    private String id;
    private String issuer;
    private Long timeToLive;
}
