package com.ludogorieSoft.villagelifefrontend.utils;

import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SitemapGenerator {
    private final VillageClient villageClient;
    private static final String BASE_VILLAGE_URL = "https://villagelife.bg/";
    private static final String VILLAGE_URL = "villages";
    private static final String PARTNERS_URL = BASE_VILLAGE_URL + VILLAGE_URL + "/partners";
    private static final String ABOUT_US_URL = BASE_VILLAGE_URL +  VILLAGE_URL + "/about-us";
    private static final String CONTACTS_URL = BASE_VILLAGE_URL +  VILLAGE_URL + "/contacts";
    private static final String GENERAL_TERMS_URL = BASE_VILLAGE_URL +  VILLAGE_URL + "/general-terms";
    private static final String CREATE_URL = BASE_VILLAGE_URL +  VILLAGE_URL + "/create";
    private static final String SHOW_URL = BASE_VILLAGE_URL +  VILLAGE_URL + "/show/";

    public XmlUrlSet generateSitemap() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, BASE_VILLAGE_URL, Priority.HIGH);
        create(xmlUrlSet, PARTNERS_URL, Priority.MEDIUM);
        create(xmlUrlSet, ABOUT_US_URL, Priority.MEDIUM);
        create(xmlUrlSet, CONTACTS_URL, Priority.MEDIUM);
        create(xmlUrlSet, GENERAL_TERMS_URL, Priority.MEDIUM);
        create(xmlUrlSet, CREATE_URL, Priority.MEDIUM);
        boolean status = true;

        villageClient.getAllApprovedVillagesByStatus(status).stream()
                .map(villageId -> new XmlUrl(SHOW_URL + villageId, Priority.HIGH))
                .forEach(xmlUrlSet::addUrl);

        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl(link, priority));
    }
}
