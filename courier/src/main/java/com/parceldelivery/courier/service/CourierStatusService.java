package com.parceldelivery.courier.service;

import com.parceldelivery.courier.model.dto.ChangeCourierStatusRequest;

import java.util.UUID;

public interface CourierStatusService {

    void changeStatus(ChangeCourierStatusRequest request, UUID courierId);
}
