package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageLivingConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "villagelife-api-villageLivingConditions",url = "http://localhost:8088/api/v1/villageLivingConditions")
public interface VillageLivingConditionClient {
    @GetMapping
    List<VillageLivingConditionDTO> getAllVillageLivingConditions();


    @GetMapping("/{id}")
    VillageLivingConditionDTO getVillageLivingConditionsById(@PathVariable("id") Long id);

    @GetMapping("/village/{id}")
    public List<VillageLivingConditionDTO> getVillageLivingConditionsByVillageId(@PathVariable("id") Long id);

    @GetMapping("/village/value/{id}")
    double getVillageLivingConditionsByVillageIdValue(@PathVariable("id") Long id);

    @GetMapping("/village/delinquencyValue/{id}")
    Double getVillagePopulationAssertionByVillageIdDelinquencyValue(@PathVariable("id") Long id);
    @GetMapping("/village/ecoValue/{id}")
     Double getVillagePopulationAssertionByVillageIdEcoValue(@PathVariable("id") Long id);

}

