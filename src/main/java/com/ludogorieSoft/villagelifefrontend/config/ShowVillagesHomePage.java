package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "villagelife-api-show-village-to-homePage",url = "http://localhost:8088/api/v1/villages")
public interface ShowVillagesHomePage {
    @GetMapping
    List<VillageDTO> getAllVillages();
}
