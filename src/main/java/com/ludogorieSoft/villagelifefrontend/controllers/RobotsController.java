package com.ludogorieSoft.villagelifefrontend.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RobotsController {

    @GetMapping("/robots.txt")
    public ResponseEntity<Resource> getRobotsTxt() {
        Resource resource = new ClassPathResource("static/robots.txt");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"robots.txt\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}