package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.PopulationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-populations",url = "http://localhost:8088/api/v1/populations")
public interface PopulationClient {
    @GetMapping
    List<PopulationDTO> getAllPopulation();

    @GetMapping("/{id}")
    PopulationDTO getPopulationById(@PathVariable("id") Long id);

    @PostMapping
    PopulationDTO createPopulation(PopulationDTO populationDTO);

    @PostMapping("/null")
    Long createPopulationWhitNullValues();

    @PutMapping("/{id}")
    PopulationDTO updatePopulation(@PathVariable("id") Long id,PopulationDTO populationDTO);

    @DeleteMapping("/{id}")
    String deletePopulationById(@PathVariable("id") Long id);
}
