package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageGroundCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "villagelife-api-ground-village-ground-categories",url = "http://localhost:8088/api/v1/villageGroundCategory")

public interface VillageGroundCategoryClient {

    @PostMapping
    void createVillageGroundCategories( @RequestBody VillageGroundCategoryDTO villageGroundCategoryDTO);
}
