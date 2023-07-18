package com.ludogorieSoft.villagelifefrontend.auth;

import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

@FeignClient(name = "villagelife-api-auth", url = "http://localhost:8088/api/v1/auth")
public interface AuthClient {
    @PostMapping("/register")
   void register(
            @Valid @RequestBody RegisterRequest request, @RequestHeader("Authorization") String token
    );

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponce> authenticate(
            @RequestBody AuthenticationRequest request
    );
}
