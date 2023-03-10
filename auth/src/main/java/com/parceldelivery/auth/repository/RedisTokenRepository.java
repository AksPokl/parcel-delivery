package com.parceldelivery.auth.repository;

import com.parceldelivery.auth.model.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<TokenEntity, String> {
}
