package com.parceldelivery.auth.service;

import com.parceldelivery.auth.model.entity.TokenEntity;
import com.parceldelivery.auth.repository.RedisTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisCacheService implements CacheService<TokenEntity> {

    private final RedisTokenRepository redisTokenRepository;

    @Override
    public TokenEntity save(@NonNull TokenEntity entity) {
        return redisTokenRepository.save(entity);
    }
}
