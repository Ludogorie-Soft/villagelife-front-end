package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeatingType {
    ELECTRICITY("heating.type.electricity"),
    HEAT_PUMP("heating.type.heat.pump"),
    SOLID_FUEL("heating.type.solid.fuel"),
    LOCAL_HEATING("heating.type.local.hating"),
    GAS("heating.type.gas");

    private final String key;
}
