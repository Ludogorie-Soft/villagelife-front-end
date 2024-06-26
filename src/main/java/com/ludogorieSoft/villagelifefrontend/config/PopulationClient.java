package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PopulationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-populations",url = "${backend.url}/populations")
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

    @GetMapping("/{villageName}/{region}")
    PopulationDTO findPopulationByVillageNameAndRegion(@PathVariable("villageName") String villageName, @PathVariable("region") String region);
}
