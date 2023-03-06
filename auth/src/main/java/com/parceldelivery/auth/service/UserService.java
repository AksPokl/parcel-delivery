package com.parceldelivery.auth.service;

import com.parceldelivery.auth.model.dto.SignupRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    UUID save(SignupRequest signupRequest);
}
