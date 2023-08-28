package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-villages",url = "${backend.url}/villages")
public interface VillageClient {
    @GetMapping
    List<VillageDTO> getAllVillages();
    @GetMapping("/{id}")
    VillageDTO getVillageById(@PathVariable("id") Long id);

    @PostMapping
    VillageDTO createVillage(VillageDTO villageDTO);

    @PostMapping("/null")
    Long createVillageWithNullValues();


    @PutMapping("/{id}")
    VillageDTO updateVillage(@PathVariable("id") Long id, VillageDTO villageDTO);

    @DeleteMapping("/{id}")
    Void deleteVillage(@PathVariable("id") Long id);

    @GetMapping("/info/{id}")
    VillageInfo getVillageInfoById(@PathVariable("id") Long id);
    @GetMapping("/update/{villageId}")
    ResponseEntity<VillageDTO> findVillageById(@PathVariable(name = "villageId") Long id);

    @PutMapping("/{id}/increase-approved-responses-count")
    public void increaseApprovedResponsesCount(@PathVariable Long id);
}
