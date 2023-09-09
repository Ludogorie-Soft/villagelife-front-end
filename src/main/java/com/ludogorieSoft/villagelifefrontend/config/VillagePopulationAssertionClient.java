package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillagePopulationAssertionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "villagelife-api-villagePopulationAssertionClient",url = "${backend.url}/villagePopulationAssertions")
public interface VillagePopulationAssertionClient {

    @GetMapping()
    List<VillagePopulationAssertionDTO> getAllVillagePopulationAssertions();

    @GetMapping("village/{id}")
    public List<VillagePopulationAssertionDTO> getVillagePopulationAssertionByVillageId(@PathVariable("id") Long id) ;

    @PostMapping
    void createVillagePopulationAssertion( @RequestBody VillagePopulationAssertionDTO villageLandscapeDTO) ;


    }
