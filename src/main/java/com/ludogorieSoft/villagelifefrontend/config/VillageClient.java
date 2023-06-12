package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-villages",url = "http://localhost:8088/api/v1/villages")
public interface VillageClient {
    @GetMapping
    List<VillageDTO> getAllVillages();

    @GetMapping("/{id}")
    VillageDTO getVillageById(@PathVariable("id") Long id);

    @PostMapping
    VillageDTO createVillage(VillageDTO villageDTO);


    @PutMapping("/{id}")
    VillageDTO updateVillage(@PathVariable("id") Long id, VillageDTO villageDTO);

    @DeleteMapping("/{id}")
    Void deleteVillage(@PathVariable("id") Long id);
}