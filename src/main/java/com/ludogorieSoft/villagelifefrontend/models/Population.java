package com.ludogoriesoft.villagelifefrontend.models;


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