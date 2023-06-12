package com.ludogoriesoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@AllArgsConstructor

public enum Children {

    BELOW_10_YEARS  ("под 10", 1),
    FROM_11_TO_20_YEARS ("11 - 20", 2),
    FROM_21_TO_50_YEARS ("21 - 50", 3),
    OVER_50_YEARS ("над 50", 4);

    private final String name;
    private final int valueAsNumber;

}
