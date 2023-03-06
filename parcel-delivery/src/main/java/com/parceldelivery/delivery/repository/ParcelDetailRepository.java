package com.parceldelivery.delivery.repository;

import com.parceldelivery.delivery.model.entity.ParcelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParcelDetailRepository extends PagingAndSortingRepository<ParcelEntity, UUID> {

    List<ParcelEntity> findAllByUserId(UUID userId, Pageable pageable);
}
