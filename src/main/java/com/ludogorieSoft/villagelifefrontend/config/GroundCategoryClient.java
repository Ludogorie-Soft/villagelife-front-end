package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.GroundCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-ground-categories",url = "${backend.url}/groundCategories")
public interface GroundCategoryClient {
    @GetMapping
    List<GroundCategoryDTO> getAllGroundCategories();

    @GetMapping("/{id}")
    GroundCategoryDTO getGroundCategoryByID(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")
    ResponseEntity<GroundCategoryDTO> getGroundCategoryByName(@PathVariable("name") String name);

    @PostMapping
    GroundCategoryDTO createGroundCategory(GroundCategoryDTO groundCategoryDTO);

    @PutMapping("/{id}")
    GroundCategoryDTO updateGroundCategory(@PathVariable("id") Long id, GroundCategoryDTO groundCategoryDTO);


    @DeleteMapping("/{id}")
    String deleteGroundCategoryById(@PathVariable("id") Long id);
}
