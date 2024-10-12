package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-property-images",url = "${backend.url}/property-images")
public interface PropertyImageClient {
    @PostMapping
    List<PropertyImageDTO> createPropertyImage(@Valid @RequestBody List<PropertyImageDTO> propertyImageDTOS);
}
