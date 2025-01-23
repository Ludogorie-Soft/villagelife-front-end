package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.BusinessCardDTOValidator;
import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AuthenticationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.ResetPasswordRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.UserEmailRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.request.VerificationRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.AuthenticationResponce;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import com.ludogorieSoft.villagelifefrontend.exceptions.AccountNotActivatedException;
import com.ludogorieSoft.villagelifefrontend.exceptions.ApiRequestException;
import com.ludogorieSoft.villagelifefrontend.exceptions.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ROLES = "roles";
    private static final String ADMIN_NEW = "adminNew";
    private static final String REDIRECT_HOME_PAGE = "redirect:/";
    private static final String REFERER = "referer";
    private static final String REDIRECT = "redirect:";
    private static final String RESET_PASSWORD_REQUEST = "resetPasswordRequest";
    private static final String USER_EMAIL = "userEmail";
    private static final String VERIFICATION_REQUEST = "verificationRequest";
    private final AuthClient authClient;
    private final BusinessCardDTOValidator businessCardDTOValidator;

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
                               BindingResult bindingResult, @RequestParam(value = "image", required = false) MultipartFile image,
                               RedirectAttributes redirectAttributes) {
        setImageBytesFromMultipartFile(request, image);

        String referer = httpRequest.getHeader(REFERER);
        if (!request.getRole().equals(Role.USER))
            businessCardDTOValidator.validate(request.getBusinessCardDTO(), bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminNew", bindingResult);
            redirectAttributes.addFlashAttribute("registrationModal", true);
            redirectAttributes.addFlashAttribute(ADMIN_NEW, request);
            return REDIRECT + referer;
        }
        try {
            String message = authClient.register(request);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);
            return "redirect:/auth/verify-verification-token";
        } catch (DuplicateEmailException ex) {
            checkDuplicateEmailException(ex, redirectAttributes, request);
        } catch (ApiRequestException e) {
            if (e.getMessage().equals("Email already used!"))
                redirectAttributes.addFlashAttribute("duplicateBusinessEmailError", "business.card.validations.email.duplicate");
        }
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminNew", bindingResult);
        redirectAttributes.addFlashAttribute("registrationModal", true);
        redirectAttributes.addFlashAttribute(ADMIN_NEW, request);
        return REDIRECT + referer;
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
        String referer = httpRequest.getHeader(REFERER);
        ResponseEntity<AuthenticationResponce> authResponse;
        try {
            authResponse = authClient.authenticate(request);
            String token = Objects.requireNonNull(authResponse.getBody()).getToken();
            ResponseEntity<AlternativeUserDTO> altUserDTO = authClient.getAdministratorInfo(AUTH_HEADER + token);
            session.setAttribute(SESSION_NAME, token);
            session.setAttribute("info", altUserDTO.getBody());
            if (altUserDTO.getBody().getRole().equals(Role.ADMIN))
                return "redirect:/admins/village";
            return REDIRECT + referer;
        } catch (AccountNotActivatedException ex) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            redirectAttributes.addFlashAttribute(ADMINS, request);
            redirectAttributes.addFlashAttribute("credentialError", "validations.credentials.not-activated");
            return REDIRECT + referer;
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("loginModal", true);
            redirectAttributes.addFlashAttribute(ADMINS, request);
            redirectAttributes.addFlashAttribute("credentialError", "validations.credentials.error");
            return REDIRECT + referer;
        }
    }


//    @GetMapping("/verify-verification-token")
//    public String verifyUser(RedirectAttributes redirectAttributes, HttpServletRequest httpRequest) {
//        String referer = httpRequest.getHeader(REFERER);
//        redirectAttributes.addFlashAttribute(VERIFICATION_REQUEST, new VerificationRequest());
//        redirectAttributes.addFlashAttribute("verificationModal", true);
//        return REDIRECT + referer;
//    }

    @GetMapping("/verify-verification-token")
    public String verifyUser(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("verificationModal", true);
        return "redirect:/";
    }

    @PostMapping("/verify-verification-token")
    public String verifyVerificationToken(@Valid @ModelAttribute("verificationRequest") VerificationRequest verificationRequest,
                                          Model model, HttpServletRequest httpRequest, RedirectAttributes redirectAttributes) {
        String referer = httpRequest.getHeader(REFERER);
        try {
            String message = authClient.verifyVerificationToken(verificationRequest);
            model.addAttribute(ATTRIBUTE_MESSAGE, message);
        } catch (ApiRequestException e) {
            redirectAttributes.addFlashAttribute(VERIFICATION_REQUEST, new VerificationRequest());
            redirectAttributes.addFlashAttribute("verificationModal", true);
            if (e.getMessage().equals("Invalid token!")) {
                redirectAttributes.addFlashAttribute("verificationTokenError", "verification.token.error");
            }
            if (e.getMessage().equals("Account activated already!")) {
                redirectAttributes.addFlashAttribute("verificationTokenError", "verification.token.already-activated");
            }
            if (e.getMessage().equals("Account not registered!")) {
                redirectAttributes.addFlashAttribute("verificationTokenError", "verification.token.not-registered");
            }
            return REDIRECT + referer;
        }
        redirectAttributes.addFlashAttribute("verificationSuccessMessage", "verification.token.success");
        return REDIRECT + referer;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute(SESSION_NAME);
        session.removeAttribute("info");
        session.invalidate();
        return REDIRECT_HOME_PAGE;
    }

    @GetMapping("/reset-password")
    public String showResetPasswordEmailForm(RedirectAttributes redirectAttributes,
                                             HttpServletRequest httpRequest) {
        String referer = httpRequest.getHeader(REFERER);
        redirectAttributes.addFlashAttribute(USER_EMAIL, new UserEmailRequest());
        redirectAttributes.addFlashAttribute("sendEmailResetPassModal", true);
        return REDIRECT + referer;
    }

    @GetMapping("/send-reset-password-email")
    public String sendResetPasswordEmail(@ModelAttribute("userEmail") UserEmailRequest userEmail, HttpSession session, RedirectAttributes redirectAttributes,
                                         HttpServletRequest httpRequest) {
        String referer = httpRequest.getHeader(REFERER);
        redirectAttributes.addFlashAttribute("emailSentModal", true);
        try {
            authClient.resetPassword(userEmail.getUserEmail());
        } catch (ApiRequestException ex) {
            redirectAttributes.addFlashAttribute("modalTitle", "email.failed.attempt");
            redirectAttributes.addFlashAttribute("modalMessage", "email.not.found");
            return REDIRECT + referer;
        }
        redirectAttributes.addFlashAttribute("modalTitle", "email.sent.title");
        redirectAttributes.addFlashAttribute("modalMessage", "email.sent.message");
        return REDIRECT + referer;
    }

    @GetMapping("/reset-password-form")
    public String resetPasswordForm(@RequestParam("token") String token, @RequestParam("userId") Long userId, Model model) {
        addAuthAttributes(model);
        model.addAttribute("subscription", new SubscriptionDTO());
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setUserId(userId);
        model.addAttribute(RESET_PASSWORD_REQUEST, resetPasswordRequest);
        return "resetPasswordFrom";
    }

    @PostMapping("/submit-new-password")
    public String submitNewPassword(@Valid @ModelAttribute("resetPasswordRequest") ResetPasswordRequest resetPasswordRequest,
                                    RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            resetPasswordRequest.setPassword(null);
            resetPasswordRequest.setRepeatedPassword(null);
            redirectAttributes.addFlashAttribute(RESET_PASSWORD_REQUEST, resetPasswordRequest);
            return "redirect:/auth/reset-password-form?token=" + resetPasswordRequest.getToken() + "&userId=" + resetPasswordRequest.getUserId();
        }
        try {
            authClient.resetPassword(resetPasswordRequest);
        } catch (ApiRequestException ex) {
            resetPasswordRequest.setPassword(null);
            resetPasswordRequest.setRepeatedPassword(null);
            redirectAttributes.addFlashAttribute(RESET_PASSWORD_REQUEST, new ResetPasswordRequest());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/auth/reset-password-form?token=" + resetPasswordRequest.getToken() + "&userId=" + resetPasswordRequest.getUserId();
        }
        redirectAttributes.addFlashAttribute("subscriptionMessage", "reset.password.success");//subscription message is for toast message
        return REDIRECT_HOME_PAGE;
    }

    private void checkDuplicateEmailException(DuplicateEmailException ex, RedirectAttributes redirectAttributes, RegisterRequest request) {
        if (ex.getMessage().contains("mobile"))
            redirectAttributes.addFlashAttribute("duplicateMobileError", "register.request.validations.mobile.duplicate");
        if (ex.getMessage().contains("email"))
            redirectAttributes.addFlashAttribute("duplicateEmailError", "register.request.validations.email.duplicate");
        if (ex.getMessage().contains("username"))
            redirectAttributes.addFlashAttribute("duplicateUsernameError", "register.request.validations.username.duplicate");
        if (!request.getRole().equals(Role.USER) && ex.getMessage().contains("email") && ex.getMessage().contains(request.getBusinessCardDTO().getEmail()))
            redirectAttributes.addFlashAttribute("duplicateBusinessEmailError", "business.card.validations.email.duplicate");
    }

    private void setImageBytesFromMultipartFile(RegisterRequest request, MultipartFile image) {
        if (!request.getRole().equals(Role.USER)) {
            byte[] imageBytes = null;
            if (image.getSize() > 0) {
                try {
                    imageBytes = image.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            request.getBusinessCardDTO().setImageBytes(imageBytes);
        }
    }

    private void addAuthAttributes(Model model) {
        if (!model.containsAttribute(ADMIN_NEW)) {
            model.addAttribute(ADMIN_NEW, new RegisterRequest());
        }
        if (!model.containsAttribute(VERIFICATION_REQUEST)) {
            model.addAttribute(VERIFICATION_REQUEST, new VerificationRequest());
        }
        if (!model.containsAttribute(RESET_PASSWORD_REQUEST)) {
            model.addAttribute(RESET_PASSWORD_REQUEST, new ResetPasswordRequest());
        }
        if (!model.containsAttribute(USER_EMAIL)) {
            model.addAttribute(USER_EMAIL, new UserEmailRequest());
        }
    }
}
