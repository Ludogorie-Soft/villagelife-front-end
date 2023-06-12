package com.ludogoriesoft.villagelifefrontend.dtos;

import com.ludogoriesoft.villagelifefrontend.enums.Children;
import com.ludogoriesoft.villagelifefrontend.enums.Foreigners;
import com.ludogoriesoft.villagelifefrontend.enums.NumberOfPopulation;
import com.ludogoriesoft.villagelifefrontend.enums.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopulationDTO {

    private Long id;
    private NumberOfPopulation numberOfPopulation;
    private Residents residents;
    private Children children;
    private Foreigners foreigners;
}
