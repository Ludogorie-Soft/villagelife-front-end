package com.ludogorieSoft.villagelifefrontend.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VillageNameProcessor {

    public static String processVillageName(String villageName) {

        String regex = "^(?:село\\s*|с\\.\\s*|Село\\s*|С\\.\\s*|СЕЛО\\s*)\\s*(.*?)(?:,|$)";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(villageName);

        if (matcher.find()) {
            villageName = matcher.group(1).trim();
        } else {
            int oblastIndex = villageName.indexOf(" обл.");
            if (oblastIndex != -1) {
                villageName = villageName.substring(0, oblastIndex).trim();
            } else {
                int commaIndex = villageName.indexOf(",");
                if (commaIndex != -1) {
                    villageName = villageName.substring(0, commaIndex).trim();
                }
            }
        }

        return villageName;
    }


}
