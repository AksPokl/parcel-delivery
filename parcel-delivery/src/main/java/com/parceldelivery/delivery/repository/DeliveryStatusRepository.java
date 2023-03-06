package com.parceldelivery.delivery.repository;

import com.parceldelivery.delivery.model.entity.DeliveryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusEntity, Long> {

}
