package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageVideoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;
import java.util.List;

@FeignClient(name = "villagelife-api-villageVideos",url = "${backend.url}/villageVideos")
public interface VillageVideoClient {
    @GetMapping("/village/{villageId}")
    List<VillageVideoDTO> getAllVideosByVillageId(Long villageId);
    @GetMapping("/approved/village/{villageId}")
    List<VillageVideoDTO> getAllApprovedVideosByVillageId(@PathVariable("villageId") Long villageId);
    @PostMapping("/video")
    void saveVideos(@RequestBody List<VillageVideoDTO> villageVideoDTOS);
}
