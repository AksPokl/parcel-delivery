package com.parceldelivery.auth.service;

public interface CacheService<T> {

    T save(T entity);
}
