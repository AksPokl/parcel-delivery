package com.parceldelivery.auth.model.dto;


import com.parceldelivery.model.request.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SignupRequest {

    private final String username;
    private final String email;
    private final String password;
    private final Role role;
}
