package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.ObjectAroundVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-object-around-village",url = "${backend.url}/objectsAroundVillage")
public interface ObjectAroundVillageClient {
    @GetMapping
    List<ObjectAroundVillageDTO> getAllObjectsAroundVillage();

    @GetMapping("/{id}")
    ObjectAroundVillageDTO getObjectAroundVillageByID(@PathVariable("id") Long id);

    @PostMapping
    ObjectAroundVillageDTO createObjectAroundVillage(ObjectAroundVillageDTO objectAroundVillageDTO);

    @PutMapping("/{id}")
    ObjectAroundVillageDTO updateObjectAroundVillage(@PathVariable("id") Long id, ObjectAroundVillageDTO objectAroundVillageDTO);

    @DeleteMapping("/{id}")
    String deleteObjectAroundVillageById(@PathVariable("id") Long id);
}
