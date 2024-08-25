package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.utils.SitemapGenerator;
import com.ludogorieSoft.villagelifefrontend.utils.XmlUrlSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class SitemapController {
    private final SitemapGenerator sitemapGenerator;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    @ResponseBody
    public XmlUrlSet  getSitemap() {
        return sitemapGenerator.generateSitemap() ;
    }
}

