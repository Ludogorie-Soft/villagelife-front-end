package com.ludogorieSoft.villagelifefrontend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.models.Population;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateUpload;
    private Boolean status;
    private List<String> images;

//    private List<ObjectVillageDTO> objectVillages;
//    private List<VillageLivingConditionDTO> villageLivingConditions;
//    private List<VillageAnswerQuestionDTO> villageAnswerQuestions;
//    private List<EthnicityVillageDTO> ethnicityVillages;
//    private List<VillageGroundCategoryDTO> villageGroundCategories;
////    private List<VillageImageDTO> villageImages;
////    private List<VillageLandscapeDTO> villageLandscapes;
//    private List<VillagePopulationAssertionDTO> villagePopulationAssertions;

}
