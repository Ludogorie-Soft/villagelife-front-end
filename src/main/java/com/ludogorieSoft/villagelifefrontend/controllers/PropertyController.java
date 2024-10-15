package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.PropertyClient;
import com.ludogorieSoft.villagelifefrontend.config.PropertyImageClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.exceptions.ImageMaxUploadSizeExceededException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {
    private PropertyClient propertyClient;
    private VillageClient villageClient;
    private static final String PROPERTY_DTO_NAME = "propertyDTO";
    private static final String PROPERTY_SAVE = "/save";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String PERMISSIONS_MESSAGE = "You do not have permissions for this page!";

    @GetMapping(value = {"/{page}", ""})
    String listProperties(Model model, @PathVariable(name = "page", required = false) Integer page) {
        int currentPage = (page != null) ? page : 0;
        model.addAttribute("pagesCount", propertyClient.getAllProperties(currentPage, 6).getTotalPages());
        model.addAttribute("properties", propertyClient.getAllProperties(currentPage, 6).stream().toList());
        return "/property/list-properties";
    }

    @GetMapping("/add")
    public String createProperty(Model model) {
        if (!model.containsAttribute(PROPERTY_DTO_NAME)) {
            model.addAttribute(PROPERTY_DTO_NAME, new PropertyDTO());
        }
        return "/property/create-property";
    }
    @PostMapping(PROPERTY_SAVE)
    public String submitProperty(@Valid @ModelAttribute("propertyDTO") PropertyDTO propertyDTO, @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("propertyImages") List<MultipartFile> propertyImages, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyDTO", bindingResult);
            redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
            return "redirect:/properties/add";
        }
        VillageDTO villageDTO = villageClient.findVillageByNameAndRegion(propertyDTO.getVillageDTO().getName() + ", " + propertyDTO.getVillageDTO().getRegion());
        if (villageDTO == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "The village is not found!");
            redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
            return "redirect:/properties/add";
        }

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
        propertyDTO.setVillageDTO(villageDTO);
        propertyDTO.setPropertyUserDTO(getLoggedPropertyUserDTO(session));
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

    public PropertyUserDTO getLoggedPropertyUserDTO(HttpSession session) {
        PropertyUserDTO loggedPropertyUserDTO = (PropertyUserDTO) session.getAttribute("info");
        return loggedPropertyUserDTO;
    }
}
