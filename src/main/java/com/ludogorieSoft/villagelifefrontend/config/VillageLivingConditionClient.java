package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageLivingConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping
     void createVillageLivingConditions(@RequestBody VillageLivingConditionDTO villageLivingConditionsDTO);

}

