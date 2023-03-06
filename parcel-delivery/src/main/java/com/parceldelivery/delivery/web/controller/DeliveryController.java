package com.parceldelivery.delivery.web.controller;

import com.parceldelivery.delivery.service.DeliveryService;
import com.parceldelivery.model.request.ChangeDeliveryStatusApiRequest;
import com.parceldelivery.model.request.UpdateDestinationApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/parcel-delivery/delivery")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Update destination of order delivery by user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination is updated by user."),
            @ApiResponse(responseCode = "400", description = "Incorrect payload."),
            @ApiResponse(responseCode = "401", description = "Delivery is not found.")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{deliveryId}/change-destination")
    public ResponseEntity<Void> changeDestination(@PathVariable("deliveryId") UUID deliveryId,
                                                  @Valid @RequestBody UpdateDestinationApiRequest request) {
        deliveryService.updateDestination(request, deliveryId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancel a parcel delivery. Can be done only by user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination is updated by user."),
            @ApiResponse(responseCode = "400", description = "Incorrect payload."),
            @ApiResponse(responseCode = "401", description = "Delivery is not found.")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{deliveryId}/cancel")
    public ResponseEntity<Void> cancelDelivery(@PathVariable("deliveryId") UUID deliveryId) {
        deliveryService.cancelDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{deliveryId}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable("deliveryId") UUID deliveryId,
                                             @Valid @RequestBody ChangeDeliveryStatusApiRequest request) {
        deliveryService.changeStatus(request, deliveryId);
        return ResponseEntity.ok().build();
    }
}
