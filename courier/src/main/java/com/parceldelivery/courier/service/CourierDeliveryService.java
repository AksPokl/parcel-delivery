package com.parceldelivery.courier.service;

import com.parceldelivery.courier.model.dto.AssignDeliveryRequest;

public interface CourierDeliveryService {

    void assignDelivery(AssignDeliveryRequest request, String token);
}
