package com.parceldelivery.auth.security;

import com.google.common.collect.ImmutableMap;
import com.parceldelivery.auth.security.service.UserDetailsImpl;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.parceldelivery.commonutil.security.SecurityConstants.EMAIL;
import static com.parceldelivery.commonutil.security.SecurityConstants.ROLES;
import static com.parceldelivery.commonutil.security.SecurityConstants.USERNAME;
import static com.parceldelivery.commonutil.security.SecurityConstants.USER_ID;


@Slf4j
@Component
@AllArgsConstructor
public class JwtGenerator {

    private final JwtProperties jwtProperties;

    public String createJwt(@NonNull Authentication authentication) {
        long nowInMillis = System.currentTimeMillis();
        Date now = new Date(nowInMillis);

        Map<String, Object> claims = toClaimsMap(authentication);

        Key signingKey = generateSigningKey();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(jwtProperties.getId())
                .setIssuedAt(now)
                .setSubject(jwtProperties.getSubject())
                .setIssuer(jwtProperties.getIssuer())
                .setClaims(claims)
                .signWith(signingKey, SignatureAlgorithm.HS256);

        if (jwtProperties.getTimeToLive() > 0) {
            jwtBuilder.setExpiration(new Date(nowInMillis + jwtProperties.getTimeToLive()));
        }

        return jwtBuilder.compact();
    }

    private Map<String, Object> toClaimsMap(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ImmutableMap.of(
                USERNAME, userDetails.getUsername(),
                EMAIL, userDetails.getEmail(),
                ROLES, userDetails.getAuthorities(),
                USER_ID, userDetails.getId()
        );
    }

    private Key generateSigningKey() {
        byte[] secret = Base64.getEncoder().encode(jwtProperties.getHmacSecret().getBytes());
        return new SecretKeySpec(secret, SignatureAlgorithm.HS256.getJcaName());
    }
}
