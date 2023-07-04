package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.ObjectAroundVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-object-around-village",url = "http://localhost:8088/api/v1/objectsAroundVillage")
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
