package com.ludogoriesoft.villagelifefrontend.models;


import com.ludogoriesoft.villagelifefrontend.enums.Children;
import com.ludogoriesoft.villagelifefrontend.enums.Foreigners;
import com.ludogoriesoft.villagelifefrontend.enums.NumberOfPopulation;
import com.ludogoriesoft.villagelifefrontend.enums.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Population {

    private Long id;
    private NumberOfPopulation numberOfPopulation;
    private Residents residents;
    private Children children;
    private Foreigners foreigners;
}