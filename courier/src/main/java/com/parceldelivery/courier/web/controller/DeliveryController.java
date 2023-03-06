package com.parceldelivery.courier.web.controller;

import com.parceldelivery.courier.model.dto.AssignDeliveryRequest;
import com.parceldelivery.courier.service.CourierDeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION;

@RestController
@RequestMapping("/parcel-delivery/courier")
@AllArgsConstructor
public class DeliveryController {

    private final CourierDeliveryService courierDeliveryService;

    @Operation(summary = "Assign delivery to courier. Can be done only via admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery is assigned to courier."),
            @ApiResponse(responseCode = "400", description = "Incorrect ids are provided.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{courierId}/assign-delivery/{deliveryId}")
    public ResponseEntity<Void> assignedDelivery(@PathVariable("courierId") UUID courierId,
                                                 @PathVariable("deliveryId") UUID deliveryId,
                                                 @RequestHeader(name = AUTHORIZATION) String token) {
        courierDeliveryService.assignDelivery(AssignDeliveryRequest.builder()
                .courierId(courierId)
                .deliveryId(deliveryId)
                .build(), token);
        return ResponseEntity.ok().build();
    }
}
