package com.parceldelivery.delivery.service;

import com.parceldelivery.model.request.CreateParcelApiRequest;
import com.parceldelivery.model.response.CreateParcelResponse;

public interface ParcelService {

    CreateParcelResponse createParcel(CreateParcelApiRequest createParcelRequest, String token);
}
