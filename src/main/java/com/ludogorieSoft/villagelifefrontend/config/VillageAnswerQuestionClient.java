package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageAnswerQuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-VillageAnswerQuestionService", url = "${backend.url}/villageAnswerQuestion")

public interface VillageAnswerQuestionClient {

    @GetMapping
    List<VillageAnswerQuestionDTO> getAllVillageAnswerQuestions();

    @GetMapping("/village/{id}")
    public List<VillageAnswerQuestionDTO> getVillageAnswerQuestionByVillageId(@PathVariable("id") Long id);

    @PostMapping
     void createVillageAnswerQuestion(@RequestBody VillageAnswerQuestionDTO villageAnswerQuestionDTO);

    @GetMapping("/answers/{questionName}")
    List<Object[]> findVillageNameAndAnswerByQuestionName(@PathVariable("questionName") String questionName, @RequestHeader("Authorization") String token);
}
