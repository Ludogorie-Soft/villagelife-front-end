package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageAnswerQuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "villagelife-api-VillageAnswerQuestionService",url = "http://localhost:8088/api/v1/villageAnswerQuestion")

public interface VillageAnswerQuestionClient {

    @GetMapping
    List<VillageAnswerQuestionDTO> getAllVillageAnswerQuestions();

    @GetMapping("/village/{id}")
    public List<VillageAnswerQuestionDTO> getVillageAnswerQuestionByVillageId(@PathVariable("id") Long id);
}
