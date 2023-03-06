package com.parceldelivery.courier.web.controller;

import com.parceldelivery.courier.service.CourierService;
import com.parceldelivery.model.request.ChangeCourierStatusApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION;

@RestController
@RequestMapping("/parcel-delivery/courier")
@AllArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @Operation(summary = "Change courier status. Can be done only via courier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courier status is changed."),
            @ApiResponse(responseCode = "400", description = "Incorrect payload is provided.")
    })
    @PreAuthorize("hasRole('COURIER')")
    @PutMapping("/change-status")
    public ResponseEntity<Void> changeStatus(@Valid @RequestBody ChangeCourierStatusApiRequest changeCourierStatusRequest,
                                             @RequestHeader(name = AUTHORIZATION) String token) {
        courierService.changeStatus(changeCourierStatusRequest, token);
        return ResponseEntity.ok().build();
    }
}
