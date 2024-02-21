package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.localization.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/locale")
public class LocaleController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MyLocaleResolver myLocaleResolver;
//    @GetMapping
//    public String getCurrentLocale(HttpServletRequest request) {
//        System.out.println("request.getLocale().toString()   " + request.getLocale().toString());
//        return request.getLocale().toString();
////        return messageSource.getMessage("hello", null, myLocaleResolver.resolveLocale(request));
//    }

    @GetMapping
    public String getInternationalPage() {
        return "hello";
    }

}
