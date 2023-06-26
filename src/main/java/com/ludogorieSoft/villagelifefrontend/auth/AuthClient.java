package com.ludogoriesoft.villagelifefrontend.auth;

import com.ludogoriesoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogoriesoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogoriesoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "villagelife-api-auth",url = "http://localhost:8088/api/v1/auth")
public interface AuthClient {
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> register(
            @RequestBody RegisterRequest request, @RequestHeader("Authorization") String token
    );
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponce> authenticate(
            @RequestBody AuthenticationRequest request
    );
}
