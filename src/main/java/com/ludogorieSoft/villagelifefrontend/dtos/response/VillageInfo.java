package com.ludogorieSoft.villagelifefrontend.dtos.response;

import com.ludogorieSoft.villagelifefrontend.dtos.ObjectVillageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageAnswerQuestionDTO;
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
    private String ethnicities;
    private List<PopulationAssertionResponse> populationAssertionResponses;
    private List<LivingConditionResponse> livingConditionResponses;
    private List<ObjectVillageDTO> objectVillages;
    private List<VillageAnswerQuestionDTO> villageAnswerQuestionDTOs;
}
