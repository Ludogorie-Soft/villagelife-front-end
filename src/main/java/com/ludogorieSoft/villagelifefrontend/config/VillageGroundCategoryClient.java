package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageGroundCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "villagelife-api-ground-village-ground-categories",url = "http://localhost:8088/api/v1/villageGroundCategory")

public interface VillageGroundCategoryClient {

    @PostMapping
    void createVillageGroundCategories( @RequestBody VillageGroundCategoryDTO villageGroundCategoryDTO);


    @PostMapping("/{villageId}/{groundCategoryId}")
    void updateVillageGroundCategory(@PathVariable Long villageId, @PathVariable Long groundCategoryId);


    @GetMapping("/check-village-exists/{villageId}")
    boolean isVillageExists(@PathVariable Long villageId);


}
