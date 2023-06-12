package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.QuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-questions",url = "http://localhost:8088/api/v1/questions")
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
