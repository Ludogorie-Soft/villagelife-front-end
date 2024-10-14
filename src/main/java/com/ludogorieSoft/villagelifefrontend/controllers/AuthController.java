package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.BusinessCardDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
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
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ROLES = "roles";
    private static final String ADMIN_NEW = "adminNew";
    private static final String REDIRECT_HOME_PAGE = "redirect:/";


    @GetMapping("/register-user")
    public String createUser(Model model) {
        RegisterRequest request = new RegisterRequest();
        request.setBusinessCardDTO(new BusinessCardDTO());
        model.addAttribute(ADMIN_NEW, request);
        model.addAttribute(ATTRIBUTE_ROLES, List.of(Role.USER, Role.AGENCY, Role.BUILDER, Role.INVESTOR));
        return "user/register_form";
    }


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
            AlternativeUserDTO admin = (AlternativeUserDTO) session.getAttribute("info");

            model.addAttribute(ADMINS, admin.getFullName());
            model.addAttribute(ADMIN_NEW, new AdministratorRequest());
            model.addAttribute(ATTRIBUTE_ROLES, Role.ADMIN);
        } else {
            throw new ApiRequestException("Unauthorized: Invalid request");
        }
        return "admin_templates/register_form";
    }

    @PostMapping("/register-user")
    public String registerUser(@Valid @ModelAttribute("adminNew") RegisterRequest request,
                               BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registrationModal", true);
            System.out.println("----1");
            redirectAttributes.addFlashAttribute(ADMIN_NEW, request);
            System.out.println("----2");
            return REDIRECT_HOME_PAGE;
        }
        String message = authClient.register(request);
        System.out.println("----3");
        redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);
        System.out.println("----4");
        return "redirect:/auth/verify-verification-token";
    }

    @PostMapping("/register")
    public String registerAdmin(@Valid @ModelAttribute("adminNew") RegisterRequest request,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            AlternativeUserDTO admin = (AlternativeUserDTO) session.getAttribute("info");
            model.addAttribute(ADMINS, admin.getFullName());
            model.addAttribute(ATTRIBUTE_ROLES, Role.values());
            return "admin_templates/register_form";
        }
        String token = (String) session.getAttribute(SESSION_NAME);
        String message = authClient.register(request, AUTH_HEADER + token);
        redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);
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
        ResponseEntity<AlternativeUserDTO> altUserDTO = authClient.getAdministratorInfo(AUTH_HEADER + token);
        session.setAttribute(SESSION_NAME, token);
        session.setAttribute("info", altUserDTO.getBody());
        if (altUserDTO.getBody().getRole().equals(Role.ADMIN))
            return "redirect:/admins/village";
        return REDIRECT_HOME_PAGE;
    }

    @GetMapping("/verify-verification-token")
    public String verifyUser(Model model) {
        model.addAttribute("verificationRequest", new VerificationRequest());
        return "user/verify-user";
    }

    @PostMapping("/verify-verification-token")
    public String verifyVerificationToken(@Valid @ModelAttribute("verificationRequest") VerificationRequest verificationRequest,
                                          BindingResult bindingResult, Model model,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("verificationRequest", new VerificationRequest());
            return "user/verify-user";
        }

        String message = authClient.verifyVerificationToken(verificationRequest);
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
        return REDIRECT_HOME_PAGE;
    }
}
