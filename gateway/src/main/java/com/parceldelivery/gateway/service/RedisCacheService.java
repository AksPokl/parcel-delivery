package com.parceldelivery.gateway.service;

import com.parceldelivery.gateway.entity.TokenEntity;
import com.parceldelivery.gateway.repository.RedisTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RedisCacheService {

    private final RedisTokenRepository redisTokenRepository;


    public Optional<TokenEntity> findById(@NonNull String id) {
        return redisTokenRepository.findById(id);
    }
}
