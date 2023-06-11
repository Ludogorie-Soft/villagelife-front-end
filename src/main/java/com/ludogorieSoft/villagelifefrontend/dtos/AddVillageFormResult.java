package com.ludogorieSoft.villagelifefrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddVillageFormResult {
    private VillageDTO villageDTO;
    private PopulationDTO populationDTO;
    private String groundCategoryName;
    private List<Long> ethnicityDTOIds;
    private List<ObjectAroundVillageDTO> objectAroundVillageDTOS;
}