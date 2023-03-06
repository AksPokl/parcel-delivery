package com.parceldelivery.auth.security;

import com.parceldelivery.auth.model.entity.TokenEntity;
import com.parceldelivery.auth.service.CacheService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtSecurityService {

    private final JwtGenerator jwtGenerator;
    private final CacheService<TokenEntity> redisCacheService;
    private final AuthenticationManager authenticationManager;

    public String createJwt(@NonNull String username, @NonNull String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.createJwt(authentication);

        TokenEntity tokenEntity = TokenEntity.builder()
                .id(UUID.randomUUID().toString())
                .authenticationToken(token)
                .username(username)
                .createdBy("SYSTEM").createdOn(LocalDateTime.now())
                .modifiedBy("SYSTEM").modifiedOn(LocalDateTime.now())
                .build();
        tokenEntity = redisCacheService.save(tokenEntity);
        return tokenEntity.getId();
    }
}
