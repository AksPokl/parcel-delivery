package com.parceldelivery.auth.service;

import com.parceldelivery.model.request.AccountSignupApiRequest;
import com.parceldelivery.model.request.SigninApiRequest;
import com.parceldelivery.model.request.UserSignupRequest;
import com.parceldelivery.model.response.LoginResponse;
import com.parceldelivery.model.response.SignupResponse;

public interface AuthService {
    SignupResponse registerUser(UserSignupRequest signUpRequest);

    SignupResponse registerAccount(AccountSignupApiRequest signUpRequest);

    LoginResponse authenticateUser(SigninApiRequest loginRequest);
}
