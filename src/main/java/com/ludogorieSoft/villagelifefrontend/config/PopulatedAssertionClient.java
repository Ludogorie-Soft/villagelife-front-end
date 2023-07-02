package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.PopulatedAssertionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-populated-assertion",url = "http://localhost:8088/api/v1/populatedAssertions")
public interface PopulatedAssertionClient {
    @GetMapping
    List<PopulatedAssertionDTO> getAllPopulatedAssertion();
    @GetMapping("/{id}")
    PopulatedAssertionDTO getPopulatedAssertionById(@PathVariable("id") Long id);
    @PostMapping
    PopulatedAssertionDTO createPopulatedAssertion(PopulatedAssertionDTO populatedAssertionDTO);
    @PutMapping("/{id}")
    PopulatedAssertionDTO updatePopulatedAssertionById(@PathVariable("id") Long id, PopulatedAssertionDTO populatedAssertionDTO);
    @DeleteMapping("/{id}")
    String deletePopulatedAssertionById(@PathVariable("id") Long id);
}
