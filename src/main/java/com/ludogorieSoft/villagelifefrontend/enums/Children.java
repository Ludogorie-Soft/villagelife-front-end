package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Children {

    BELOW_10  ("children.below_10", 1),
    FROM_11_TO_20 ("children.from_11_to_20", 2),
    FROM_21_TO_50 ("children.from_21_to_50", 3),
    OVER_50 ("children.over_50", 4);

    private final String name;
    private final int valueAsNumber;


    public static Children getByValueAsString(String value) {
        if (value == null) {
            return null;
        }
        int intValue = Integer.parseInt(value);
        for (Children children : Children.values()) {
            if (children.getValueAsNumber() == intValue) {
                return children;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }



}
