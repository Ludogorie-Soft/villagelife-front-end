package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.PropertyStatsClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/property-stats")
public class PropertyStatsController {
    private PropertyStatsClient propertyStatsClient;

    @PostMapping("/increment-views/{propertyId}")
    public String incrementPropertyViews(@PathVariable("propertyId") Long propertyId) {
        propertyStatsClient.incrementPropertyViews(propertyId);
        return "redirect:/properties/show/" + propertyId;
    }
}
