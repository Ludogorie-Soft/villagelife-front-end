package com.ludogorieSoft.villagelifefrontend.dtos.response;

import com.ludogorieSoft.villagelifefrontend.dtos.PopulationDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VillageInfo {
    private VillageDTO villageDTO;
    private PopulationDTO populationDTO;
    private String ethnicities;
    private List<PopulationAssertionResponse> populationAssertionResponses;
    private List<LivingConditionResponse> livingConditionResponses;
    private List<ObjectVillageResponse> objectVillageResponses;
    private List<AnswersQuestionResponse> answersQuestionResponses;
}
