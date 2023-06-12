package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.LivingConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-living-condition",url = "http://localhost:8088/api/v1/livingConditions")
public interface LivingConditionClient {
    @GetMapping
    List<LivingConditionDTO> getAllLivingConditions();
    @GetMapping("/{id}")
    LivingConditionDTO getLivingConditionsByID(@PathVariable("id") Long id);
    @PostMapping
    LivingConditionDTO createLivingConditions(LivingConditionDTO livingConditionDTO);
    @PutMapping("/{id}")
    LivingConditionDTO updateLivingCondition(@PathVariable("id") Long id, LivingConditionDTO livingConditionDTO);
    @DeleteMapping("/{id}")
    String deleteLivingConditionById(@PathVariable("id") Long id);
}
