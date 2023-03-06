package com.parceldelivery.delivery.repository;

import com.parceldelivery.delivery.model.entity.ParcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, UUID> {

}
