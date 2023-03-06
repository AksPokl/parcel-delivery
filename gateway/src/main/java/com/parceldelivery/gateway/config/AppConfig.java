package com.parceldelivery.gateway.config;

import com.parceldelivery.gateway.filter.AuthenticationPrefilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Value("${app.authURI}")
    public String authURI;

    @Value("${app.courierURI}")
    public String courierURI;

    @Value("${app.parcelDeliveryURI}")
    public String parcelDeliveryURI;

    @Value("${app.gateway.excludedUrls}")
    private String excludeUrlsString;

    @Bean
    @Qualifier("excludedUrls")
    public List<String> excludedUrls() {
        return Arrays.stream(excludeUrlsString.split(",")).collect(Collectors.toList());
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationPrefilter authFilter) {
        return builder.routes()
                .route("auth_route", r -> r.path("/parcel-delivery/auth/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationPrefilter.Config())))
                        .uri(authURI))
                .route("courier_route", r -> r.path("/parcel-delivery/courier/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationPrefilter.Config())))
                        .uri(courierURI))
                .route("parcel_route", r -> r.path("/parcel-delivery/parcel/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationPrefilter.Config())))
                        .uri(parcelDeliveryURI))
                .route("delivery_route", r -> r.path("/parcel-delivery/delivery/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationPrefilter.Config())))
                        .uri(parcelDeliveryURI))
                .build();
    }
}
