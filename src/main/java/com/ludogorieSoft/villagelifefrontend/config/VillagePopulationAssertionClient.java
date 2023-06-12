package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillagePopulationAssertionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "villagelife-api-villagePopulationAssertionClient",url = "http://localhost:8088/api/v1/villagePopulationAssertions")
public interface VillagePopulationAssertionClient {

    @GetMapping()
    List<VillagePopulationAssertionDTO> getAllVillagePopulationAssertions();

}
