package com.ludogorieSoft.villagelifefrontend.auth;

import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

@FeignClient(name = "villageLife-api-auth", url = "${backend.url}/auth")
public interface AuthClient {
    @PostMapping("/register")
   String register(
            @Valid @RequestBody RegisterRequest request, @RequestHeader("Authorization") String token
    );

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponce> authenticate(
            @RequestBody AuthenticationRequest request
    );
    @GetMapping("/get-info")
    ResponseEntity<AdministratorDTO> getAdministratorInfo(@RequestHeader("Authorization") String token);
    @GetMapping("/check")
    public ResponseEntity<String> authorizeAdminToken(@RequestHeader("Authorization") String token);
}
