package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.PropertyValidator;
import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.config.PropertyClient;
import com.ludogorieSoft.villagelifefrontend.config.PropertyImageClient;
import com.ludogorieSoft.villagelifefrontend.config.UserSavedPropertyClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import com.ludogorieSoft.villagelifefrontend.exceptions.ApiRequestException;
import com.ludogorieSoft.villagelifefrontend.utils.PageableResponse;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ludogorieSoft.villagelifefrontend.enums.PropertyTransferType.RENT;
import static com.ludogorieSoft.villagelifefrontend.enums.PropertyTransferType.SALE;

@Controller
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {
    private PropertyClient propertyClient;
    private VillageClient villageClient;
    private PropertyValidator propertyValidator;
    private AuthClient authClient;
    private static final String PROPERTY_DTO_NAME = "propertyDTO";
    private static final String PROPERTY_SAVE= "/save";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String PERMISSIONS_MESSAGE = "You do not have permissions for this page!";
    private PropertyImageClient propertyImageClient;
    private UserSavedPropertyClient userSavedPropertyClient;
    private static final String SUBSCRIPTION_ATTRIBUTE = "subscription";

    @GetMapping(value = {"/{page}", ""})
    String listProperties(Model model, @PathVariable(name = "page", required = false) Integer page){
        int currentPage = (page != null) ? page : 0;
        addAuthAttributes(model);
        model.addAttribute("pagesCount", propertyClient.getAllProperties(currentPage, 6).getTotalPages());
        model.addAttribute("properties", propertyClient.getAllProperties(currentPage, 6).stream().toList());
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        return "/property/list-properties";
    }
    @GetMapping("/add-sale")
    public String createPropertyForSale(Model model,RedirectAttributes redirectAttributes,HttpSession session) {
        AlternativeUserDTO loggedUser = (AlternativeUserDTO) session.getAttribute("info");
        if (loggedUser == null) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            return "redirect:/properties";
        }
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPropertyTransferType(SALE);
        addAuthAttributes(model);
        if (!model.containsAttribute(PROPERTY_DTO_NAME)) {
            model.addAttribute(PROPERTY_DTO_NAME, propertyDTO);
        }
        return "/property/create-property-sale";
    }
    @GetMapping("/add-rent")
    public String createPropertyForRent(Model model,RedirectAttributes redirectAttributes,HttpSession session) {
        AlternativeUserDTO loggedUser = (AlternativeUserDTO) session.getAttribute("info");
        if (loggedUser == null) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            return "redirect:/properties";
        }
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPropertyTransferType(RENT);
        addAuthAttributes(model);
        if (!model.containsAttribute(PROPERTY_DTO_NAME)) {
            model.addAttribute(PROPERTY_DTO_NAME, propertyDTO);
        }
        return "/property/create-property-rent";
    }
    @PostMapping(PROPERTY_SAVE)
    public String submitProperty(@ModelAttribute("propertyDTO") PropertyDTO propertyDTO, @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("propertyImages") List<MultipartFile> propertyImages, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        AlternativeUserDTO loggedUser = (AlternativeUserDTO) session.getAttribute("info");
        byte[] mainImageBytes = convertImageToBytes(mainImage);
        propertyDTO.setMainImageBytes(mainImageBytes);

        List<byte[]> propertyImagesBytes =propertyImages.stream()
                .map(this::convertImageToBytes)
                .toList();
        if (propertyDTO.getImages() == null || propertyDTO.getImages().isEmpty()) {
            propertyDTO.setImages(new ArrayList<>());
        }

        for (byte[] imageBytes : propertyImagesBytes) {
            PropertyImageDTO propertyImageDTO = new PropertyImageDTO();
            propertyImageDTO.setPropertyImageBytes(imageBytes);
            propertyDTO.getImages().add(propertyImageDTO);
        }
        propertyValidator.validate(propertyDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyDTO", bindingResult);
            redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
            if (propertyDTO.getPropertyTransferType() == SALE) {
                return "redirect:/properties/add-sale";
            }
            else if (propertyDTO.getPropertyTransferType() == RENT){
                return "redirect:/properties/add-rent";
            }
        }
        VillageDTO villageDTO = villageClient.findVillageByNameAndRegion(propertyDTO.getVillageDTO().getName() + ", " + propertyDTO.getVillageDTO().getRegion());
        propertyDTO.setVillageDTO(villageDTO);
        propertyDTO.setAlternativeUserDTO(loggedUser);
        propertyClient.createProperty(propertyDTO);
        return "redirect:/properties";
    }

    private byte[] convertImageToBytes(MultipartFile image) {
        byte[] imageData = null;
        if (image.getSize() > 0) {
            try {
                imageData = image.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageData;
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
