package com.ludogorieSoft.villagelifefrontend.localization;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Component
public class HtmlRenderer {
    private static final String HTML_FILE_NAME = "hello";
    private final TemplateEngine templateEngine;

    public HtmlRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    public String renderHtmlFromTemplate(Locale locale, String username){
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username",username);
        context.setVariable("lang",locale.getLanguage());
        context.setVariable("url", "https://simplelocalize.io");
        return templateEngine.process(HTML_FILE_NAME,context);

    }
}
