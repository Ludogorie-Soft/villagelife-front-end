package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.PropertyClient;
import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {
    private PropertyClient propertyClient;
    private static final String SUBSCRIPTION_ATTRIBUTE = "subscription";

    @GetMapping(value = {"/{page}", ""})
    String listProperties(Model model, @PathVariable(name = "page", required = false) Integer page){
        int currentPage = (page != null) ? page : 0;
        model.addAttribute("pagesCount", propertyClient.getAllProperties(currentPage, 6).getTotalPages());
        model.addAttribute("properties", propertyClient.getAllProperties(currentPage, 6).stream().toList());
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        return "/property/list-properties";
    }
}
