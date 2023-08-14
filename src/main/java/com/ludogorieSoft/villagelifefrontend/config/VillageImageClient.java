package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "villagelife-api-village-images",url = "${backend.url}/villageImages")
public interface VillageImageClient {
    @GetMapping("/village/{villageId}/images")
    ResponseEntity<List<String>> getAllImagesForVillage(@PathVariable Long villageId, @RequestParam boolean status, @RequestParam String date);

    @GetMapping("/all")
    ResponseEntity<List<VillageDTO>> getAllVillageDTOsWithImages();

    @GetMapping("/approved")
    ResponseEntity<List<VillageDTO>> getAllApprovedVillageDTOsWithImages();

}
