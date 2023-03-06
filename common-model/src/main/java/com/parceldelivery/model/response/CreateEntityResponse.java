package com.parceldelivery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateEntityResponse {

    private final UUID id;
}
