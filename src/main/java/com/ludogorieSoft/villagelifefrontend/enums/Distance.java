package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Distance {
    IN_THE_VILLAGE("distance.in_the_village"),
    ON_10_KM("distance.on_10_km"),
    ON_11_TO_30KM("distance.on_11_to_30km"),
    ON_31_TO_50_KM("distance.on_31_to_50_km"),
    OVER_50_KM("distance.over_50_km");

    private final String name;
}
