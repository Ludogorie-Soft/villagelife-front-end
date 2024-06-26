package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.RegionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "villagelife-api-regions",url = "${backend.url}/regions")
public interface RegionClient {
    @GetMapping
    List<RegionDTO> getAllRegions();

    @GetMapping("/{id}")
    RegionDTO getRegionById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    RegionDTO updateRegion(@PathVariable("id") Long id, RegionDTO regionDTO);

    @DeleteMapping("/{id}")
    String deleteRegionById(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")
    Boolean existsRegionByName(@PathVariable("name") String name);
}
