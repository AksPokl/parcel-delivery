package com.parceldelivery.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateMessageMetadataRequest {

    private final UUID id;
    private final Long tag;
}
