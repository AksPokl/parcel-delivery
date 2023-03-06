package com.parceldelivery.courier.service;

import com.parceldelivery.courier.client.AuthClient;
import com.parceldelivery.courier.model.dto.CreateCourierRequest;
import com.parceldelivery.model.request.AccountSignupApiRequest;
import com.parceldelivery.model.request.CreateAccountApiRequest;
import com.parceldelivery.model.request.Role;
import com.parceldelivery.model.response.CreateEntityResponse;
import com.parceldelivery.model.response.SignupResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthClient authClient;
    private final CourierService courierService;

    @Override
    public CreateEntityResponse createCourier(@NonNull CreateAccountApiRequest request, @NonNull String token) {
        log.info("Started creating courier account");
        SignupResponse signupResponse = authClient.signup(token, toSignupRequest(request));

        if (signupResponse != null) {
            courierService.create(CreateCourierRequest.builder()
                    .userId(signupResponse.getId())
                    .username(request.getUsername())
                    .build());

            log.info("Finished creating courier account");
            return CreateEntityResponse.builder()
                    .id(signupResponse.getId())
                    .build();
        }

        throw new RuntimeException("Courier is not created.");
    }

    private AccountSignupApiRequest toSignupRequest(CreateAccountApiRequest request) {
        return AccountSignupApiRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .role(Role.ROLE_COURIER.name())
                .build();
    }
}
