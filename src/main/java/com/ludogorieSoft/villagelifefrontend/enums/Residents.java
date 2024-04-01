package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Residents {
    UP_TO_2_PERCENT ("residents.up_to_2_percent", 1),
    FROM_2_TO_5_PERCENT ("residents.from_2_to_5_percent", 2),
    FROM_6_TO_10_PERCENT ("residents.from_6_to_10_percent", 3),
    FROM_11_TO_20_PERCENT ("residents.from_11_to_20_percent", 4),
    FROM_21_TO_30_PERCENT ("residents.from_21_to_30_percent", 5),
    OVER_30_PERCENT ("residents.up_to_30_percent", 6);

    private final String name;
    private final int valueAsNumber;
}
