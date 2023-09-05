package com.ludogorieSoft.villagelifefrontend.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "villagelife-api-validation",url = "http://localhost:8088/api/v1/validations")
public interface ValidationUtilsClient {

    @GetMapping("/check-name")
    Boolean usernameCheck(@RequestParam String name) ;
}
