package com.parceldelivery.courier.client;

import com.parceldelivery.model.response.DeliveryDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(value = "parcel-delivery",
        url = "${feign.client.parcel-delivery.url}",
        configuration = CustomErrorDecoder.class)
public interface ParcelDeliveryClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/parcel-delivery/delivery/{deliveryId}",
            produces = "application/json")
    DeliveryDetailResponse getDeliveryDetails(@RequestParam("deliveryId") UUID deliveryId,
                                              @RequestHeader("Authorization") String token);
}
