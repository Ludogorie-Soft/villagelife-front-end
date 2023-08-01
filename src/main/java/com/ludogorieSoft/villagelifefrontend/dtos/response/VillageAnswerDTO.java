package com.ludogorieSoft.villagelifefrontend.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageAnswerQuestionDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VillageAnswerDTO {
    Map<Long, Map<LocalDateTime, Boolean>> groupedAnswers;
    List<VillageResponse> nestedVillageResponses;

}
