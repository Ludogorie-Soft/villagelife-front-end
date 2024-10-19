package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstructionType {
    BRICKS("Тухли"),
    PANEL("Панел"),
    WOOD("Дърво");
    private final String name;
}