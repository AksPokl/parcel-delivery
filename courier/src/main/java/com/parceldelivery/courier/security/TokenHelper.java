package com.parceldelivery.courier.security;

import com.parceldelivery.commonutil.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenHelper {

    @Value("${jwt.hmacSecret}")
    private String secret;

    public UUID getUserId(String token) {
        return UUID.fromString(JwtUtils.getUserIdFromJwtToken(JwtUtils.parseToken(token), secret));
    }
}
