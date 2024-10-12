package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.utils.PageableResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "villagelife-api-properties",url = "${backend.url}/properties")
public interface PropertyClient {
    @GetMapping("/{page}/{elements}")
    PageableResponse<PropertyDTO> getAllProperties(@PathVariable("page") int page, @PathVariable("elements") int elements);
    @PostMapping
    PropertyDTO createProperty(@Valid @RequestBody PropertyDTO propertyDTO);
}
