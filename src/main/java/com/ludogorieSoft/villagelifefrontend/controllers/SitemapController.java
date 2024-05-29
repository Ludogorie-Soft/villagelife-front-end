package com.ludogorieSoft.villagelifefrontend.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitemapController {

    @GetMapping("/sitemap.xml")
    public ResponseEntity<Resource> getSitemap() {
        Resource resource = new ClassPathResource("static/sitemap.xml");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"sitemap.xml\"")
                .contentType(MediaType.APPLICATION_XML)
                .body(resource);
    }
}

