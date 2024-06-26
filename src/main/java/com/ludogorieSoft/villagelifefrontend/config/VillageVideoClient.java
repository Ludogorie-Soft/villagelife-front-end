package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageVideoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-villageVideos",url = "${backend.url}/villageVideos")
public interface VillageVideoClient {
    @GetMapping("/village/{villageId}")
    List<VillageVideoDTO> getAllVideosByVillageId(@PathVariable("villageId") Long villageId);
    @GetMapping("/approved/village/{villageId}")
    List<VillageVideoDTO> getAllApprovedVideosByVillageId(@PathVariable("villageId") Long villageId);
}
