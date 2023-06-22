package com.ludogoriesoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Children {

    BELOW_10  ("под 10", 1),
    FROM_11_TO_20 ("11 - 20", 2),
    FROM_21_TO_50 ("21 - 50", 3),
    OVER_50 ("над 50", 4),

    ADD ("ДОБАВЕН", 5);

    private final String name;
    private final int valueAsNumber;

    public static List<Children> getAllChildrenConstants() {
        return Arrays.asList(Children.values());
    }

    public static Children getByValue(int value) {
        for (Children child : Children.values()) {
            if (child.getValueAsNumber() == value) {
                return child;
            }
        }
        throw new IllegalArgumentException("Invalid Children value: " + value);
    }

    public static Children getByValueAsString(String value) {
        int intValue = Integer.parseInt(value);
        for (Children children : Children.values()) {
            if (children.getValueAsNumber() == intValue) {
                return children;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }





    public String getValue() {
        return String.valueOf(valueAsNumber);
    }

    public static boolean isValidValue(String value) {
        for (Children children : Children.values()) {
            if (children.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }


}
