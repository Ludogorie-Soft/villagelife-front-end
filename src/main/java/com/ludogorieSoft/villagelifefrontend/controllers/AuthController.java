package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.BusinessCardDTOValidator;
import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import com.ludogorieSoft.villagelifefrontend.exceptions.ApiRequestException;
import com.ludogorieSoft.villagelifefrontend.exceptions.DuplicateEmailException;
import com.ludogorieSoft.villagelifefrontend.exceptions.UsernamePasswordException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
    private final BusinessCardDTOValidator businessCardDTOValidator;
    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ROLES = "roles";
    private static final String ADMIN_NEW = "adminNew";
    private static final String REDIRECT_HOME_PAGE = "redirect:/";

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
    public String registerUser(@Valid @ModelAttribute("adminNew") RegisterRequest request, HttpServletRequest httpRequest,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        String referer = httpRequest.getHeader("referer");
        if (!request.getRole().equals(Role.USER)) businessCardDTOValidator.validate(request.getBusinessCardDTO(), bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminNew", bindingResult);
            redirectAttributes.addFlashAttribute("registrationModal", true);
            redirectAttributes.addFlashAttribute(ADMIN_NEW, request);
            return "redirect:" + referer;
            //return REDIRECT_HOME_PAGE;
        }
        try {
            String message = authClient.register(request);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);
            return "redirect:/auth/verify-verification-token";
        } catch (DuplicateEmailException ex) {
            if (ex.getMessage().contains("mobile"))
                redirectAttributes.addFlashAttribute("duplicateMobileError", "register.request.validations.mobile.duplicate");
            if (ex.getMessage().contains("email"))
                redirectAttributes.addFlashAttribute("duplicateEmailError", "register.request.validations.email.duplicate");
            if (ex.getMessage().contains("username"))
                redirectAttributes.addFlashAttribute("duplicateUsernameError", "register.request.validations.username.duplicate");
            if (!request.getRole().equals(Role.USER) && ex.getMessage().contains("email") && ex.getMessage().contains(request.getBusinessCardDTO().getEmail()))
                redirectAttributes.addFlashAttribute("duplicateBusinessEmailError", "business.card.validations.email.duplicate");
        } catch (ApiRequestException e) {
            if (e.getMessage().equals("Email already used!"))
                redirectAttributes.addFlashAttribute("duplicateBusinessEmailError", "business.card.validations.email.duplicate");
        }
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminNew", bindingResult);
        redirectAttributes.addFlashAttribute("registrationModal", true);
        redirectAttributes.addFlashAttribute(ADMIN_NEW, request);
        //return REDIRECT_HOME_PAGE;
        return "redirect:" + referer;
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
    public String authenticateAdmin(@ModelAttribute("admins") AuthenticationRequest request, HttpSession session,
                                    RedirectAttributes redirectAttributes, HttpServletRequest httpRequest) {
        String referer = httpRequest.getHeader("referer");
        try{
            ResponseEntity<AuthenticationResponce> authResponse;
            authResponse = authClient.authenticate(request);
            String token = Objects.requireNonNull(authResponse.getBody()).getToken();
            ResponseEntity<AlternativeUserDTO> altUserDTO = authClient.getAdministratorInfo(AUTH_HEADER + token);
            session.setAttribute(SESSION_NAME, token);
            session.setAttribute("info", altUserDTO.getBody());
            if (altUserDTO.getBody().getRole().equals(Role.ADMIN))
                return "redirect:/admins/village";
            //return REDIRECT_HOME_PAGE;
            return "redirect:" + referer;
        } catch (UsernamePasswordException ex) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            redirectAttributes.addFlashAttribute(ADMINS, request);
            redirectAttributes.addFlashAttribute("credentialError", "validations.credentials.error");
            //return REDIRECT_HOME_PAGE;
            return "redirect:" + referer;
        }
    }

    @GetMapping("/verify-verification-token")
    public String verifyUser(RedirectAttributes redirectAttributes, HttpServletRequest httpRequest) {
        String referer = httpRequest.getHeader("referer");
        redirectAttributes.addFlashAttribute("verificationRequest", new VerificationRequest());
        redirectAttributes.addFlashAttribute("verificationModal", true);
        return "redirect:" + referer;
    }

    @PostMapping("/verify-verification-token")
    public String verifyVerificationToken(@Valid @ModelAttribute("verificationRequest") VerificationRequest verificationRequest,
                                          Model model, HttpServletRequest httpRequest, RedirectAttributes redirectAttributes) {
        String referer = httpRequest.getHeader("referer");
        try {
            String message = authClient.verifyVerificationToken(verificationRequest);
            model.addAttribute(ATTRIBUTE_MESSAGE, message);
        } catch (ApiRequestException e) {
            if (e.getMessage().equals("Invalid token!")) {
                redirectAttributes.addFlashAttribute("verificationRequest", new VerificationRequest());
                redirectAttributes.addFlashAttribute("verificationModal", true);
                redirectAttributes.addFlashAttribute("verificationTokenError", "verification.token.error");
                return "redirect:" + referer;
            }
        }
        redirectAttributes.addFlashAttribute("verificationSuccessMessage", "verification.token.success");
        return "redirect:" + referer;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute(SESSION_NAME);
        session.removeAttribute("info");
        session.invalidate();
        return REDIRECT_HOME_PAGE;
    }
}
