package com.parceldelivery.delivery.service;

import com.parceldelivery.model.request.ChangeDeliveryStatusApiRequest;

import java.util.UUID;

public interface DeliveryStatusService {

    void createStatus(ChangeDeliveryStatusApiRequest request, UUID deliveryId);
}
