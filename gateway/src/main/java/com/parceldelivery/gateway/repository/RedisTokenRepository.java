package com.parceldelivery.gateway.repository;

import com.parceldelivery.gateway.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<TokenEntity, String> {
}
