package com.ludogoriesoft.villagelifefrontend.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { Exception.class })
    protected String handleGenericException(Model model) {
        model.addAttribute("error", "Възникна неочаквана грешка в приложението!!!");
        return "HomePage";
    }

}