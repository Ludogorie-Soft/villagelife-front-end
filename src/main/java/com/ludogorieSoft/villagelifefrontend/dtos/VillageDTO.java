package com.ludogoriesoft.villagelifefrontend.dtos;

import com.ludogoriesoft.villagelifefrontend.models.Population;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VillageDTO {

    private Long id;
    private String name;
    private Population population;
    private String region;
    private int populationCount;
    private PopulationDTO populationDTO;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date dateUpload;
    private boolean status;

}
