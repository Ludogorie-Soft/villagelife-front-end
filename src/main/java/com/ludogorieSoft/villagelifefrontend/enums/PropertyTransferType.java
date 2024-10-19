package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyTransferType {
    SALE("Продажба"),
    RENT("Наем");
    private final String name;
}