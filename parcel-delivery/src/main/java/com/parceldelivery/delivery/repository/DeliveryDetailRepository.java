package com.parceldelivery.delivery.repository;

import com.parceldelivery.delivery.model.entity.DeliveryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryDetailRepository extends PagingAndSortingRepository<DeliveryEntity, UUID> {

    List<DeliveryEntity> findAllByCourierId(UUID userId, Pageable pageable);
}
