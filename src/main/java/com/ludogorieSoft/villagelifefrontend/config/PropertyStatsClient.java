package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyStatsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "villagelife-api-property-stats",url = "${backend.url}/property-stats")
public interface PropertyStatsClient {

    @PutMapping("/{propertyId}/increment-views")
    PropertyStatsDTO incrementPropertyViews(@PathVariable Long propertyId);
}
