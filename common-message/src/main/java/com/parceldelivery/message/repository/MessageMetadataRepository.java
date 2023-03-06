package com.parceldelivery.message.repository;


import com.parceldelivery.message.model.entity.MessageMetadataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageMetadataRepository extends CrudRepository<MessageMetadataEntity, UUID> {
}
