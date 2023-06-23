package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.RegionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "villagelife-api-regions",url = "http://localhost:8088/api/v1/regions")
public interface RegionClient {
    @GetMapping
    List<RegionDTO> getAllRegions();

    @GetMapping("/{id}")
    RegionDTO getRegionById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    RegionDTO updateRegion(@PathVariable("id") Long id, RegionDTO regionDTO);

    @DeleteMapping("/{id}")
    String deleteRegionById(@PathVariable("id") Long id);
}
