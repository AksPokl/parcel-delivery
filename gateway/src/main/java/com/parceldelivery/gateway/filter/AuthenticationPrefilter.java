package com.parceldelivery.gateway.filter;

import com.parceldelivery.commonutil.security.JwtUtils;
import com.parceldelivery.gateway.entity.TokenEntity;
import com.parceldelivery.gateway.service.RedisCacheService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    private final List<String> excludedUrls;
    private final RedisCacheService redisCacheService;

    public AuthenticationPrefilter(@Qualifier("excludedUrls") List<String> excludedUrls,
                                   RedisCacheService redisCacheService) {
        super(Config.class);
        this.excludedUrls = excludedUrls;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (isSecureUrl(request)) {
                String token = JwtUtils.parseToken(request);

                Optional<TokenEntity> tokenEntity = redisCacheService.findById(token);
                tokenEntity.ifPresent(entity -> exchange.getRequest().mutate().header("Authorization", String.format("Bearer %s", entity.getAuthenticationToken())));
                return chain.filter(exchange);
            }

            return chain.filter(exchange);
        };
    }

    private boolean isSecureUrl(ServerHttpRequest request) {
        return excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    @NoArgsConstructor
    public static class Config {
    }
}
