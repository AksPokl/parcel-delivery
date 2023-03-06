package com.parceldelivery.courier.client;

import com.parceldelivery.model.request.AccountSignupApiRequest;
import com.parceldelivery.model.response.SignupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "auth", url = "${feign.client.auth.url}", configuration = CustomErrorDecoder.class)
public interface AuthClient {

    @RequestMapping(method = RequestMethod.POST,
            value = "/parcel-delivery/auth/signup/account",
            produces = "application/json")
    SignupResponse signup(@RequestHeader("Authorization") String token,
                          @RequestBody AccountSignupApiRequest signupRequest);
}
