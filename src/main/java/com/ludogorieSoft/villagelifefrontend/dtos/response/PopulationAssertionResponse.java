package com.ludogorieSoft.villagelifefrontend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopulationAssertionResponse {
    private String populationAssertionName;
    private double percentage;
}
