package com.ludogorieSoft.villagelifefrontend.dtos;

import com.ludogorieSoft.villagelifefrontend.models.Population;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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
    private List<String> images;

}
