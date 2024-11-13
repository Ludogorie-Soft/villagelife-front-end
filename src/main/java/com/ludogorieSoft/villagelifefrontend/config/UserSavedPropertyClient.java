package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.UserSavedPropertyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "villagelife-api-user-saved-properties", url = "${backend.url}/user-saved-properties")
public interface UserSavedPropertyClient {
    @PostMapping
    UserSavedPropertyDTO createUserSavedProperty(@Valid @RequestBody UserSavedPropertyDTO UserSavedPropertyDTO);

    @PostMapping("/toggle/property/{propertyId}/user/{userId}")
    UserSavedPropertyDTO togglePropertySavedByPropertyIdAndAlternativeUserId(@PathVariable("propertyId") Long propertyId, @PathVariable("userId") Long userId);

    @GetMapping("/property/{propertyId}/user/{userId}")
    Boolean isPropertySavedByPropertyIdAndAlternativeUserId(@PathVariable("propertyId") Long propertyId, @PathVariable("userId") Long userId);
}
