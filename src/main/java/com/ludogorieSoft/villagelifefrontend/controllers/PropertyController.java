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

@Controller
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {
    private PropertyClient propertyClient;
    private VillageClient villageClient;
    private PropertyImageClient propertyImageClient;
    private static final String PROPERTY_DTO_NAME = "propertyDTO";
    private static final String PROPERTY_SAVE = "/save";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String PERMISSIONS_MESSAGE = "You do not have permissions for this page!";
    private static final long MAX_FILE_SIZE = (long) 5 * 1024 * 1024;

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

    //    @PostMapping(PROPERTY_SAVE)
//    public String submitProperty(@Valid @ModelAttribute("propertyDTO") PropertyDTO propertyDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session){
//        try {
//            if (bindingResult.hasErrors()) {
//                bindingResult.getAllErrors().forEach(error -> {
//                    System.out.println("Error: " + error.getObjectName() + " - " + error.getDefaultMessage());
//                });
//                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyDTO", bindingResult);
//                redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
//                return "redirect:/properties/add";
//            }
//            VillageDTO villageDTO = villageClient.findVillageByNameAndRegion(propertyDTO.getVillageDTO().getName() + ", " + propertyDTO.getVillageDTO().getRegion());
//            if (villageDTO == null) {
//                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Селището не е намерено.");
//                redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
//                return "redirect:/properties/add";
//            }
//            propertyDTO.setVillageDTO(villageDTO);
//            propertyDTO.setPropertyUserDTO(getLoggedPropertyUserDTO(session));
//            List<byte[]> imageBytes = new ArrayList<>();
//            if (images.get(0).getSize() > 0) {
//
//               if (bindingResult.hasErrors()) {
//                   redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyDTO", bindingResult);
//                   redirectAttributes.addFlashAttribute(PROPERTY_DTO_NAME, propertyDTO);
//                   return "redirect:/properties/add";
//               }
//               hasExceededFileSize(images);
//              convertImagesToBytes(images, imageBytes);
//           }
//           propertyDTO.setImageBytes(imageBytes);
//            propertyClient.createProperty(propertyDTO);
//           // propertyImageClient.createPropertyImage(propertyDTO.getImages());
//            return "redirect:/properties";
//        } catch (FeignException exception) {
//            if (exception.status() == 401) {
//                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
//                        PERMISSIONS_MESSAGE);
//                return REDIRECT_INDEX;
//            }
//        }
//        return "redirect:/properties";
//    }
    @PostMapping(PROPERTY_SAVE)
    public String submitProperty(@Valid @ModelAttribute("propertyDTO") PropertyDTO propertyDTO, @RequestParam("mainImage") MultipartFile mainImage, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
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

        propertyDTO.setVillageDTO(villageDTO);
        propertyDTO.setPropertyUserDTO(getLoggedPropertyUserDTO(session));
        propertyClient.createProperty(propertyDTO);
        return "redirect:/properties";
    }

    private void hasExceededFileSize(List<MultipartFile> images) throws ImageMaxUploadSizeExceededException {
        long totalSize = images.stream()
                .mapToLong(MultipartFile::getSize)
                .sum();
        if (totalSize > PropertyController.MAX_FILE_SIZE) {
            throw new ImageMaxUploadSizeExceededException("File size should not exceed 5 MB");
        }
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
