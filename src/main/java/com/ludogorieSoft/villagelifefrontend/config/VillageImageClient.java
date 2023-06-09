package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageImageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "villagelife-api-village-images",url = "http://localhost:8088/api/v1/villageImages")
public interface VillageImageClient {
    @GetMapping("/village/{villageId}/images")
    ResponseEntity<List<String>> getAllImagesForVillage(@PathVariable Long villageId);

    @GetMapping("/all")
    ResponseEntity<List<VillageImageResponse>> getAllVillageImageResponses();
}
