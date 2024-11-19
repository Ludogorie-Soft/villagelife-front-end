package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.PropertyClient;
import com.ludogorieSoft.villagelifefrontend.config.PropertyImageClient;
import com.ludogorieSoft.villagelifefrontend.config.RegionClient;
import com.ludogorieSoft.villagelifefrontend.config.UserSavedPropertyClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.RegionDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import com.ludogorieSoft.villagelifefrontend.enums.ConstructionType;
import com.ludogorieSoft.villagelifefrontend.enums.OwnershipType;
import com.ludogorieSoft.villagelifefrontend.enums.PropertyType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {
    private PropertyClient propertyClient;
    private PropertyImageClient propertyImageClient;
    private UserSavedPropertyClient userSavedPropertyClient;
    private RegionClient regionClient;
    private static final String SUBSCRIPTION_ATTRIBUTE = "subscription";

    @GetMapping(value = {"/{page}", ""})
    String listProperties(Model model, @PathVariable(name = "page", required = false) Integer page){
        int currentPage = (page != null) ? page : 0;
        addAuthAttributes(model);
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
        model.addAttribute("pagesCount", propertyClient.getAllProperties(currentPage, 6).getTotalPages());
        model.addAttribute("properties", propertyClient.getAllProperties(currentPage, 6).stream().toList());
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        addSearchPropertyAttributes(model);
        return "/property/list-properties";
    }

    @GetMapping("/show/{id}")
    public String showPropertyById(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
        PropertyDTO propertyDTO = propertyClient.getPropertyWithMainImageById(id);
        List<PropertyImageDTO> propertyImageDTOs = propertyImageClient.getAllPropertyImagesByPropertyId(id);
        propertyImageDTOs.add(new PropertyImageDTO(null, propertyDTO.getImageUrl(), null, null));

        addAuthAttributes(model);
        model.addAttribute("property", propertyDTO);
        model.addAttribute("propertyImages", propertyImageDTOs);
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());

        AlternativeUserDTO loggedUser = (AlternativeUserDTO) session.getAttribute("info");
        boolean hasSaved = false;
        if (loggedUser != null) {
            hasSaved = userSavedPropertyClient.isPropertySavedByPropertyIdAndAlternativeUserId(id, loggedUser.getId());
        }
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("hasSaved", hasSaved);

        return "/property/property";
    }

    private void addSearchPropertyAttributes(Model model) {
        model.addAttribute("propertyTypes", List.of(PropertyType.values()));
        model.addAttribute("constructionTypes", List.of(ConstructionType.values()));
        model.addAttribute("ownershipTypes", List.of(OwnershipType.values()));
    }

    @PostMapping("/toggle-user-saved-property/{propertyId}")
    public String toggleUserSavedProperty(@PathVariable("propertyId") Long propertyId, HttpSession session, RedirectAttributes redirectAttributes) {
        AlternativeUserDTO loggedUser = (AlternativeUserDTO) session.getAttribute("info");

        if (loggedUser == null) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            return "redirect:/properties/show/" + propertyId;
        }

        userSavedPropertyClient.togglePropertySavedByPropertyIdAndAlternativeUserId(propertyId, loggedUser.getId());
        return "redirect:/properties/show/" + propertyId;
    }

    private void addAuthAttributes(Model model) {
        if (!model.containsAttribute("adminNew")) {
            model.addAttribute("adminNew", new RegisterRequest());
        }
        if (!model.containsAttribute("verificationRequest")) {
            model.addAttribute("verificationRequest", new VerificationRequest());
        }
    }
}
