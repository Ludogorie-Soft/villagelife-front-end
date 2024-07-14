package com.ludogorieSoft.villagelifefrontend.utils;

import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.exceptions.SitemapGeneratorException;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    @SneakyThrows
    public void createSitemap() throws MalformedURLException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = ZonedDateTime.now(ZoneId.of("UTC")).format(formatter);

        File sitemapDir = new File("src/main/resources/static");
        if (!sitemapDir.exists()) {
            sitemapDir.mkdirs();
        }

        boolean status = true;
        WebSitemapGenerator webSitemapGenerator = WebSitemapGenerator.builder(BASE_VILLAGE_URL, sitemapDir)
                .build();
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(BASE_VILLAGE_URL).lastMod(date).priority(1.0).build());
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(PARTNERS_URL).lastMod(date).priority(0.8).build());
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(ABOUT_US_URL).lastMod(date).priority(0.8).build());
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(CONTACTS_URL).lastMod(date).priority(0.8).build());
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(GENERAL_TERMS_URL).lastMod(date).priority(0.6).build());
        webSitemapGenerator.addUrl(new WebSitemapUrl.Options(CREATE_URL).lastMod(date).priority(0.8).build());

        villageClient.getAllApprovedVillagesByStatus(status).stream()
                .map(villageId -> {
                    try {
                        return new WebSitemapUrl.Options(SHOW_URL + villageId)
                                .lastMod(date)
                                .priority(1.0)
                                .build();
                    } catch (MalformedURLException | ParseException e) {
                        throw new SitemapGeneratorException(e.getMessage());
                    }
                })
                .forEach(webSitemapGenerator::addUrl);

        webSitemapGenerator.write();
    }
}
