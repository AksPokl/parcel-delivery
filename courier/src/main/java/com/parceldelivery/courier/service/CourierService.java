package com.parceldelivery.courier.service;

import com.parceldelivery.courier.model.dto.CreateCourierRequest;
import com.parceldelivery.courier.model.message.ChangeCourierStatusMessageRequest;
import com.parceldelivery.model.request.ChangeCourierStatusApiRequest;

public interface CourierService {

    void create(CreateCourierRequest request);

    void changeStatus(ChangeCourierStatusApiRequest request, String token);

    void changeStatus(ChangeCourierStatusMessageRequest request);
}
