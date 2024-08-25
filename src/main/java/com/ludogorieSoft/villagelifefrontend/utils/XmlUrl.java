package com.ludogorieSoft.villagelifefrontend.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
@Getter
@Setter
@NoArgsConstructor
public class XmlUrl {
    @XmlElement(name = "loc", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String loc;

    @XmlElement(name = "lastmod", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String lastmod;

    @XmlElement(name = "priority", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String priority;

    public XmlUrl(String loc, Priority priority) {
        this.loc = loc;
        this.priority = priority.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.lastmod = ZonedDateTime.now(ZoneId.of("UTC")).format(formatter);
    }

}
