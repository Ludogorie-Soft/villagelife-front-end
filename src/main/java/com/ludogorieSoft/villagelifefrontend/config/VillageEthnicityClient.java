package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.EthnicityVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@FeignClient(name = "villagelife-api-villagesEthnicityClient",url = "http://localhost:8088/api/v1/villageEthnicities")

public interface VillageEthnicityClient {
    @GetMapping
    public ResponseEntity<List<EthnicityVillageDTO>> getAllEthnicityVillages();

    @GetMapping("/{id}")
     EthnicityVillageDTO getEthnicityVillageById(@PathVariable("id") Long id) ;
    @GetMapping("/village/{id}")
     EthnicityVillageDTO getEthnicityVillageByVillageId(@PathVariable("id") Long id);
    @PostMapping
     void createEthnicityVillage( @RequestBody EthnicityVillageDTO ethnicityVillageDTO);
}
