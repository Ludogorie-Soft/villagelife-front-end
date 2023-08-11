package com.ludogorieSoft.villagelifefrontend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/locale")
public class LocaleController {

//    @GetMapping
//    public String getCurrentLocale(HttpServletRequest request) {
//        System.out.println("request.getLocale().toString()   " + request.getLocale().toString());
//        return request.getLocale().toString();
//    }
}
