package com.parceldelivery.message.service;


import com.parceldelivery.message.model.CreateMessageMetadataRequest;
import com.parceldelivery.message.model.entity.MessageMetadataEntity;
import com.parceldelivery.message.repository.MessageMetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageMetadataServiceImpl implements MessageMetadataService {

    private final MessageMetadataRepository messageMetadataRepository;

    @Override
    public boolean isMessageAlreadySent(UUID messageId) {
        return messageMetadataRepository.findById(messageId).isPresent();
    }

    @Transactional
    @Override
    public void saveMessage(CreateMessageMetadataRequest request) {
        MessageMetadataEntity metadata = new MessageMetadataEntity();
        metadata.setId(request.getId());
        metadata.setTag(request.getTag());
        metadata.setCreatedAt(LocalDateTime.now());
        messageMetadataRepository.save(metadata);
    }
}
