package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.utils.SitemapGenerator;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.MalformedURLException;

@Controller
@AllArgsConstructor
public class SitemapController {
    private final SitemapGenerator sitemapGenerator;

    @GetMapping("/sitemap.xml")
    public ResponseEntity<Resource> getSitemap() throws MalformedURLException {
        Resource resource = new ClassPathResource("target/src/main/resources/static/sitemap.xml");

        sitemapGenerator.createSitemap();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"sitemap.xml\"")
                .contentType(MediaType.APPLICATION_XML)
                .body(resource);
    }
}

