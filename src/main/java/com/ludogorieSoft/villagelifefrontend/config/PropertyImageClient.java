package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "villagelife-api-property-images",url = "${backend.url}/property-images")
public interface PropertyImageClient {
    @GetMapping("/property/{propertyId}")
    List<PropertyImageDTO> getAllPropertyImagesByPropertyId(@PathVariable("propertyId") Long propertyId);
}
