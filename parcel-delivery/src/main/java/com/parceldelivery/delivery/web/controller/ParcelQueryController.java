package com.parceldelivery.delivery.web.controller;

import com.parceldelivery.delivery.service.ParcelDetailQueryService;
import com.parceldelivery.model.response.DeliveryDetailResponse;
import com.parceldelivery.model.response.ParcelDeliveryDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION;

@RestController
@RequestMapping("/parcel-delivery")
@AllArgsConstructor
public class ParcelQueryController {

    private final ParcelDetailQueryService parcelDetailQueryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/parcel/{parcelId}")
    public ResponseEntity<ParcelDeliveryDetailResponse> getUserParcelDeliveryDetail(@PathVariable("parcelId") UUID parcelId) {
        return ResponseEntity.ok(parcelDetailQueryService.getUserParcelDeliveryDetail(parcelId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/parcel")
    public ResponseEntity<List<ParcelDeliveryDetailResponse>> getParcelDeliveryDetails(@RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "5") int size,
                                                                                       @RequestHeader(name = AUTHORIZATION) String token) {
        return ResponseEntity.ok(parcelDetailQueryService.getUserParcelDeliveryDetails(token, PageRequest.of(page, size)));
    }


    @PreAuthorize("hasRole('COURIER')")
    @GetMapping("/delivery")
    public ResponseEntity<List<ParcelDeliveryDetailResponse>> getCourierParcelDeliveryDetails(@RequestParam(defaultValue = "0") int page,
                                                                                              @RequestParam(defaultValue = "5") int size,
                                                                                              @RequestHeader(name = AUTHORIZATION) String token) {
        return ResponseEntity.ok(parcelDetailQueryService.getCourierDeliveryDetails(token, PageRequest.of(page, size)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<DeliveryDetailResponse> getParcelDeliveryDetail(@PathVariable("deliveryId") UUID deliveryId) {
        return ResponseEntity.ok(parcelDetailQueryService.getDeliveryDetail(deliveryId));
    }
}
