package com.parceldelivery.auth.web.controller;


import com.parceldelivery.auth.service.AuthService;
import com.parceldelivery.model.request.AccountSignupApiRequest;
import com.parceldelivery.model.request.SigninApiRequest;
import com.parceldelivery.model.request.UserSignupRequest;
import com.parceldelivery.model.response.LoginResponse;
import com.parceldelivery.model.response.SignupResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/parcel-delivery/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Sign up a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is registered."),
            @ApiResponse(responseCode = "400", description = "Incorrect input..")
    })
    @PostMapping("/signup/user")
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody UserSignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @Operation(summary = "Create a new account. Only possible by admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account is registered."),
            @ApiResponse(responseCode = "400", description = "Incorrect input..")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup/account")
    public ResponseEntity<SignupResponse> registerAccount(@Valid @RequestBody AccountSignupApiRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerAccount(signUpRequest));
    }

    @Operation(summary = "Sign in via username and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is signed in."),
            @ApiResponse(responseCode = "400", description = "Bad credentials.")
    })
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody SigninApiRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }
}
