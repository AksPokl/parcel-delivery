package com.parceldelivery.courier.repository;

import com.parceldelivery.courier.model.entity.CourierStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierStatusRepository extends JpaRepository<CourierStatusEntity, Long> {
}
