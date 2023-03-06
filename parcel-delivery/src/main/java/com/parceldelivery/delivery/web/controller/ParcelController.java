package com.parceldelivery.delivery.web.controller;

import com.parceldelivery.delivery.service.ParcelService;
import com.parceldelivery.model.request.CreateParcelApiRequest;
import com.parceldelivery.model.response.CreateParcelResponse;
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
@RequestMapping("/parcel-delivery/parcel")
@AllArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<CreateParcelResponse> createParcel(@Valid @RequestBody CreateParcelApiRequest request,
                                                             @RequestHeader(name = AUTHORIZATION) String token) {
        return ResponseEntity.ok(parcelService.createParcel(request, token));
    }
}
