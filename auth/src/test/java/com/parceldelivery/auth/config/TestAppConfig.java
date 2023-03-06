package com.parceldelivery.auth.config;

import com.parceldelivery.auth.service.RedisCacheService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisKeyValueAdapter;

@TestConfiguration
public class TestAppConfig {

    @MockBean
    private RedisCacheService redisCacheService;
    @MockBean
    private RedisKeyValueAdapter redisKeyValueAdapter;
}
