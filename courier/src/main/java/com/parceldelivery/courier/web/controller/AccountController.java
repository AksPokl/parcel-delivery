package com.parceldelivery.courier.web.controller;

import com.parceldelivery.courier.service.AccountService;
import com.parceldelivery.model.request.CreateAccountApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION;

@RestController
@RequestMapping("/parcel-delivery/courier")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a courier account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courier account is created."),
            @ApiResponse(responseCode = "400", description = "Incorrect payload is provided.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CreateEntityResponse> registerCourier(@Valid @RequestBody CreateAccountApiRequest request,
                                                                @RequestHeader(name = AUTHORIZATION) String token) {
        return ResponseEntity.ok(accountService.createCourier(request, token));
    }
}
