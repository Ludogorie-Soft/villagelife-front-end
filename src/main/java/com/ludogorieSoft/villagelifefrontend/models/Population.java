package com.ludogorieSoft.villagelifefrontend.models;



import com.ludogorieSoft.villagelifefrontend.enums.Children;
import com.ludogorieSoft.villagelifefrontend.enums.Foreigners;
import com.ludogorieSoft.villagelifefrontend.enums.NumberOfPopulation;
import com.ludogorieSoft.villagelifefrontend.enums.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Population {

    private Long id;

    private NumberOfPopulation numberOfPopulation;

    private Residents residents;

    private Children children;

    private Foreigners foreigners;
}
