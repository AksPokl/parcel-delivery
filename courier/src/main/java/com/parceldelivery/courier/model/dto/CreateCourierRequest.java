package com.parceldelivery.courier.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateCourierRequest {

    private final String username;
    private final UUID userId;
}
