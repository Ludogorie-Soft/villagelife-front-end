package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import com.ludogorieSoft.villagelifefrontend.exceptions.ApiRequestException;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ADMINS = "admins";

    @GetMapping("/register")
    public String createAdministrator(Model model, HttpSession session) {
        String token = (String) session.getAttribute(SESSION_NAME);
        ResponseEntity<String> auth;
        try {
            auth = authClient.authorizeAdminToken(AUTH_HEADER + token);
        } catch (HttpStatusCodeException e) {
            throw new ApiRequestException("An error occurred while communicating with the API");
        }
        if (auth.getStatusCode().is2xxSuccessful()) {
            AdministratorDTO admin = (AdministratorDTO) session.getAttribute("info");

            model.addAttribute(ADMINS, admin.getFullName());
            model.addAttribute("adminNew", new AdministratorRequest());
            model.addAttribute("roles", Role.ADMIN);
        } else {
            throw new ApiRequestException("Unauthorized: Invalid request");
        }
        return "admin_templates/register_form";
    }

    @PostMapping("/register")
    public String registerAdmin(@Valid @ModelAttribute("adminNew") RegisterRequest request,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            AdministratorDTO admin = (AdministratorDTO) session.getAttribute("info");
            model.addAttribute(ADMINS, admin.getFullName());
            model.addAttribute("roles", Role.values());
            return "admin_templates/register_form";
        }
        String token = (String) session.getAttribute(SESSION_NAME);
        String message = authClient.register(request, AUTH_HEADER + token);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admins";
    }

    @GetMapping("/login")
    public String showAdminLogin(Model model) {
        model.addAttribute(ADMINS, new AuthenticationRequest());
        return "admin_templates/admin_login";
    }

    @PostMapping("/authenticate")
    public String authenticateAdmin(@ModelAttribute("admins") AuthenticationRequest request, HttpSession session) {
        ResponseEntity<AuthenticationResponce> authResponse;
        authResponse = authClient.authenticate(request);
        String token = Objects.requireNonNull(authResponse.getBody()).getToken();
        ResponseEntity<AdministratorDTO> administratorDTO = authClient.getAdministratorInfo(AUTH_HEADER + token);
        session.setAttribute(SESSION_NAME, token);
        session.setAttribute("info", administratorDTO.getBody());
        return "redirect:/admins/village";
    }

}
