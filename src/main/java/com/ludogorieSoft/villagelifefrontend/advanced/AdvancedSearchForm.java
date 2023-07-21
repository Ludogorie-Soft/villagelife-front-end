package com.ludogorieSoft.villagelifefrontend.advanced;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdvancedSearchForm {

    private List<String> livingConditionDTOS;

    private List<String> objectAroundVillageDTOS;

    private String children;




}
