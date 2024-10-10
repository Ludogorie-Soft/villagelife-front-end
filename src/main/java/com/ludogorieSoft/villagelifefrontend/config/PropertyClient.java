package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.utils.PageableResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "villagelife-api-properties",url = "${backend.url}/properties")
public interface PropertyClient {
    @GetMapping("/{page}/{elements}")
    PageableResponse<PropertyDTO> getAllProperties(@PathVariable("page") int page, @PathVariable("elements") int elements);

    @GetMapping("/village/{villageId}")
    List<PropertyDTO> getAllPropertiesByVillageId(@PathVariable("villageId") Long villageId);

    @GetMapping("/{id}")
    PropertyDTO getPropertyWithMainImageById(@PathVariable("id") Long id);
}
