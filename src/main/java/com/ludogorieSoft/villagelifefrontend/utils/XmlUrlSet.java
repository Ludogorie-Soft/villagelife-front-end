package com.ludogorieSoft.villagelifefrontend.utils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrlSet {

    @XmlElement(name = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private Collection<XmlUrl> xmlUrls = new ArrayList<>();

    public void addUrl(XmlUrl xmlUrl) {
        xmlUrls.add(xmlUrl);
    }

    public Collection<XmlUrl> getXmlUrls() {
        return xmlUrls;
    }

    public void setXmlUrls(Collection<XmlUrl> xmlUrls) {
        this.xmlUrls = xmlUrls;
    }
}
