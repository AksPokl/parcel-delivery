package com.parceldelivery.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourierStatusMessage implements Message {

    private UUID id;
    private String deliveryId;
    private String courierId;
    private String status;
}
