package com.ludogorieSoft.villagelifefrontend.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleFeignException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Invalid request");
        return new ModelAndView("/admin_templates/error");
    }

    @ExceptionHandler(NoConsentException.class)
    public ModelAndView handleNoConsentException(NoConsentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("consentMessage", ex.getMessage());
        return new ModelAndView("redirect:/villages/create");
    }

    @ExceptionHandler(ApiRequestException.class)
    public ModelAndView handleApiRequestException(ApiRequestException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/villages/home-page");
    }
}

