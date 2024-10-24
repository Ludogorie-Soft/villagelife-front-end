package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeatingType {
    //    Електричество
//
//    Английски: Electricity
//    Немски: Elektrizität
//            Термопомпа
//
//    Английски: Heat pump
//    Немски: Wärmepumpe
//    Твърдо гориво
//
//    Английски: Solid fuel
//    Немски: Festbrennstoff
//    Локално парно
//
//    Английски: Local heating
//    Немски: Lokale Heizung
//    Газ
//
//    Английски: Gas
//    Немски: Gas
    ELECTRICITY("heating.type.electricity"),
    HEAT_PUMP("heating.type.heat.pump"),
    SOLID_FUEL("heating.type.solid.fuel"),
    LOCAL_HEATING("heating.type.local.hating"),
    GAS("heating.type.gas");
//    OTHER("heating.type.other");

    private final String key;
}
