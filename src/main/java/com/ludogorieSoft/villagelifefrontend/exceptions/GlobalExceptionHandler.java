package com.ludogorieSoft.villagelifefrontend.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    String homePage = "HomePage";
    String redirecting = "redirect:/auth/login";
    String message = "errorMessage";
    String error = "error";
    String message2 = "message";

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        if (ex.getMessage().equals("Duplicate entry error")) {
            redirectAttributes.addFlashAttribute(message2, ex.getMessage());
            return "redirect:/admins";
        } else {
            redirectAttributes.addFlashAttribute(message2, ex.getMessage());
            return "redirect:/admins/village";
        }
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException(FeignException ex, Model model) {
        model.addAttribute("errorMessage", "Unauthorized: Invalid request");
        return "/admin_templates/error";
    }

    @ExceptionHandler(value = {NullPointerException.class})
    protected String handleNullPointerException(Model model) {
        model.addAttribute("error", "Възникна неочаквана грешка в приложението!!!");
        return "HomePage";
    }

    @ExceptionHandler(value = {Exception.class})
    protected String handleGenericException(Model model) {
        model.addAttribute("error", "Възникна неочаквана грешка в приложението!!!");
        return "HomePage";
    }

}

