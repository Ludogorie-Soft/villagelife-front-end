package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.EthnicityVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-villagesEthnicityClient",url = "http://localhost:8088/api/v1/villageEthnicities")

public interface VillageEthnicityClient {
    @GetMapping
    public ResponseEntity<List<EthnicityVillageDTO>> getAllEthnicityVillages();

    @GetMapping("/{id}")
     EthnicityVillageDTO getEthnicityVillageById(@PathVariable("id") Long id) ;

    @GetMapping("/village/{id}")
    List<EthnicityVillageDTO> getVillageEthnicityByVillageId(@PathVariable("id") Long id);
    @PostMapping
     void createEthnicityVillage( @RequestBody EthnicityVillageDTO ethnicityVillageDTO);

    @GetMapping("/check-existence")
    boolean checkExistence(@RequestParam Long villageId, @RequestParam Long ethnicityId);
}
