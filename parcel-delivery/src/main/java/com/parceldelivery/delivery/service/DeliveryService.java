package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.model.dto.CreateDeliveryRequest;
import com.parceldelivery.delivery.model.message.ChangeDeliveryStatusMessageRequest;
import com.parceldelivery.model.request.ChangeDeliveryStatusApiRequest;
import com.parceldelivery.model.request.UpdateDestinationApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;

import java.util.UUID;

public interface DeliveryService {

    CreateEntityResponse createDelivery(CreateDeliveryRequest request);

    void updateDestination(UpdateDestinationApiRequest request, UUID deliveryId);

    void cancelDelivery(UUID deliveryId);

    void changeStatus(ChangeDeliveryStatusApiRequest request, UUID deliveryId);

    void changeStatus(ChangeDeliveryStatusMessageRequest request);
}
