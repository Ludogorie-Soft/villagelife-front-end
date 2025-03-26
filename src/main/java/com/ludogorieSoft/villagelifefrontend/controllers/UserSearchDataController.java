package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.UserSearchDataClient;
import com.ludogorieSoft.villagelifefrontend.dtos.UserSearchDataDTO;
import com.ludogorieSoft.villagelifefrontend.exceptions.AccessDeniedException;
import com.ludogorieSoft.villagelifefrontend.exceptions.ApiRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/user-search-data")
public class UserSearchDataController {
    private UserSearchDataClient userSearchDataClient;
    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";

    @PostMapping
    public String createUserSearchData(UserSearchDataDTO userSearchDataDTO, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            throw new AccessDeniedException("You have no permissions to this request!");
        }
        if (userSearchDataDTO.getRegionName().equals("")) userSearchDataDTO.setRegionName(null);
        if (userSearchDataDTO.getVillageName().equals("")) userSearchDataDTO.setVillageName(null);
        try {
            userSearchDataClient.createUserSearchData(AUTH_HEADER + token, userSearchDataDTO);
            redirectAttributes.addFlashAttribute("successMessage", "success.message");
        } catch (ApiRequestException ex) {
            String errorMessage = null;
            if (ex.getMessage().equals("Village not found!")) {
                errorMessage = "village.not.found.error";
            } else if (ex.getMessage().equals("Region not found!")) {
                errorMessage = "region.not.fount.error";
            } else if (ex.getMessage().equals("Search name cannot be null or blank!")) {
                errorMessage = "blank.search.name.error";
            } else if (ex.getMessage().equals("Search name can not be more than 50 signs long!")) {
                errorMessage = "long.search.name.error";
            } else if (ex.getMessage().equals("User search data with the same search name and user already exists!")) {
                errorMessage = "used.search.name.error";
            }
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            redirectAttributes.addFlashAttribute("userSearchDataDTO", userSearchDataDTO);
        }
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }
}
