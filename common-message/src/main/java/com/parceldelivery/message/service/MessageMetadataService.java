package com.parceldelivery.message.service;

import com.parceldelivery.message.model.CreateMessageMetadataRequest;

import java.util.UUID;

public interface MessageMetadataService {

    boolean isMessageAlreadySent(UUID messageId);

    void saveMessage(CreateMessageMetadataRequest request);
}
