package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.QuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-questions",url = "${backend.url}/questions")
public interface QuestionClient {
    @GetMapping
    List<QuestionDTO> getAllQuestions();

    @GetMapping("/{id}")
    QuestionDTO getQuestionById(@PathVariable("id") Long id);

    @PostMapping
    QuestionDTO createQuestion(QuestionDTO questionDTO);


    @PutMapping("/{id}")
    QuestionDTO updateQuestion(@PathVariable("id") Long id, QuestionDTO questionDTO);


    @DeleteMapping("/{id}")
    String deleteQuestionById(@PathVariable("id") Long id);
}
