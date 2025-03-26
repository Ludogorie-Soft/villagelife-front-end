package com.ludogorieSoft.villagelifefrontend.auth;

import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.ResetPasswordRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "villageLife-api-auth", url = "${backend.url}/auth")
public interface AuthClient {
    @PostMapping("/register")
    String register(
            @Valid @RequestBody RegisterRequest request, @RequestHeader("Authorization") String token
    );

    @PostMapping("/register")
    String register(
            @Valid @RequestBody RegisterRequest request);

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponce> authenticate(
            @RequestBody AuthenticationRequest request
    );

    @GetMapping("/get-info")
    ResponseEntity<AlternativeUserDTO> getAdministratorInfo(@RequestHeader("Authorization") String token);

    @GetMapping("/check")
    public ResponseEntity<String> authorizeAdminToken(@RequestHeader("Authorization") String token);

    @PostMapping("/verify-verification-token")
    public String verifyVerificationToken(@RequestBody VerificationRequest verificationRequest);

    @PostMapping("/send-reset-password-email")
    String resetPassword(@RequestParam("email") String email);

    @PostMapping("/reset-password")
    String resetPassword(@RequestBody ResetPasswordRequest request);
}
