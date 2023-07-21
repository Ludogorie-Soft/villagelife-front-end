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

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        String message = "message";
        if (ex.getMessage().equals("Duplicate entry error")) {
            redirectAttributes.addFlashAttribute(message, ex.getMessage());
            return "redirect:/admins";
        } else {
            redirectAttributes.addFlashAttribute(message, ex.getMessage());
            return "redirect:/admins/village";
        }
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException(FeignException ex, RedirectAttributes redirectAttributes) {
        String redirecting = "redirect:/auth/login";
        String message = "message";
        if (ex.status() == HttpStatus.FORBIDDEN.value()) {
            redirectAttributes.addFlashAttribute(message, "Wrong password or username! Please try again!");
            return redirecting;
        } else if (ex.status() == HttpStatus.UNAUTHORIZED.value()) {
            redirectAttributes.addFlashAttribute(message, "Your session has expired! Please sign in again!");
            return redirecting;
        } else if (ex.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            redirectAttributes.addFlashAttribute(message, "You have to sign in!");
            return redirecting;
        } else {
            redirectAttributes.addFlashAttribute(message, "Something went wrong! Please try again!");
            return redirecting;
        }
    }


    @ExceptionHandler(value = {Exception.class})
    protected String handleGenericException(Model model) {
        model.addAttribute("error", "Възникна неочаквана грешка в приложението!!!");
        return "HomePage";
    }
}

