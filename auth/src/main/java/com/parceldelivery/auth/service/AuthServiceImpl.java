package com.parceldelivery.auth.service;

import com.parceldelivery.auth.model.dto.SignupRequest;
import com.parceldelivery.auth.security.JwtSecurityService;
import com.parceldelivery.model.request.AccountSignupApiRequest;
import com.parceldelivery.model.request.GeneralSignupRequest;
import com.parceldelivery.model.request.Role;
import com.parceldelivery.model.request.SigninApiRequest;
import com.parceldelivery.model.request.UserSignupRequest;
import com.parceldelivery.model.response.LoginResponse;
import com.parceldelivery.model.response.SignupResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtSecurityService jwtSecurityService;

    @Override
    public SignupResponse registerUser(@NonNull UserSignupRequest request) {
        SignupRequest signupRequest = toSignupRequest(request)
                .role(Role.ROLE_USER)
                .build();
        UUID userId = userService.save(signupRequest);
        return SignupResponse.builder()
                .id(userId)
                .build();
    }

    @Override
    public SignupResponse registerAccount(AccountSignupApiRequest request) {
        SignupRequest signupRequest = toSignupRequest(request)
                .role(Role.valueOf(request.getRole()))
                .build();
        UUID userId = userService.save(signupRequest);
        return SignupResponse.builder()
                .id(userId)
                .build();
    }

    @Override
    public LoginResponse authenticateUser(@NonNull SigninApiRequest request) {
        String token = jwtSecurityService.createJwt(request.getUsername(), request.getPassword());

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    private SignupRequest.SignupRequestBuilder toSignupRequest(GeneralSignupRequest request) {
        return SignupRequest.builder()
                .password(request.getPassword())
                .email(request.getEmail())
                .username(request.getUsername());
    }
}
