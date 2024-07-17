package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.utils.SitemapGenerator;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@AllArgsConstructor
public class SitemapController {
    private final SitemapGenerator sitemapGenerator;

    @GetMapping("/sitemap.xml")
    public ResponseEntity<FileSystemResource> getSitemap() {
        try {
            // Generate the sitemap
            sitemapGenerator.createSitemap();
            // Path to the generated sitemap in the target directory
            File sitemapFile = new File("/src/main/resources/static/sitemap.xml");
            if (!sitemapFile.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            FileSystemResource resource = new FileSystemResource(sitemapFile);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"sitemap.xml\"")
                    .contentType(MediaType.APPLICATION_XML)
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

