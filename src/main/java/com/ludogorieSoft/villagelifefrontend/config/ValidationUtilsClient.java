package com.ludogorieSoft.villagelifefrontend.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "villagelife-api-validation",url = "${backend.url}/validations")
public interface ValidationUtilsClient {

    @GetMapping("/check-name")
    Boolean usernameCheck(@RequestParam String name);

    @GetMapping("/check-number")
    Boolean numberCheck(@RequestParam String number);
}
