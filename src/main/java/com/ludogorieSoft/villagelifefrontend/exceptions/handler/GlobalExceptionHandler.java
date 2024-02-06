package com.ludogorieSoft.villagelifefrontend.exceptions.handler;

import com.ludogorieSoft.villagelifefrontend.exceptions.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoConsentException.class)
    public ModelAndView handleNoConsentException(NoConsentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("consentMessage", ex.getMessage());
        return new ModelAndView("/add-village");
    }

    @ExceptionHandler(ApiRequestException.class)
    public ModelAndView handleApiRequestException(ApiRequestException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/villages/home-page");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return new ModelAndView("redirect:/auth/login");
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ModelAndView handleTokenExpiredException(TokenExpiredException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return new ModelAndView("redirect:/auth/login");
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView handleDuplicateEmailException(DuplicateEmailException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return new ModelAndView("redirect:/auth/register");
    }
    @ExceptionHandler(ImageMaxUploadSizeExceededException.class)
    public ModelAndView handleImageMaxUploadSizeExceededException(Model model) {
        model.addAttribute("errorMessage", "File size should not exceed 10 MB.");
        return new ModelAndView("/admin_templates/error");
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(Model model) {
        model.addAttribute("errorMessage", "File size should not exceed 10 MB.");
        return new ModelAndView("/admin_templates/error");
    }
}

