package com.ludogorieSoft.villagelifefrontend.utils;

public enum Priority {
    HIGH("1.0"), MEDIUM("0.8");

    private final String value;

    Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}