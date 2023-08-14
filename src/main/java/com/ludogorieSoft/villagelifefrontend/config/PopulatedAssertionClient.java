package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PopulatedAssertionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-populated-assertion",url = "${backend.url}/populatedAssertions")
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
