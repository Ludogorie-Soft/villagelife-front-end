package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-filter", url = "http://localhost:8088/api/v1/filter")
public interface FilterClient {

    @GetMapping("/{name}")
    List<VillageDTO> getVillageByName(@PathVariable(name = "name") String name);



//    @GetMapping("/searchVillages")
//    List<VillageDTO> searchVillagesByCriteria(
//            @RequestParam List<String> objectAroundVillageDTOS,
//            @RequestParam List<String> livingConditionDTOS,
//            @RequestParam Children children);


    @GetMapping("/searchVillages")
    List<VillageDTO> searchVillagesByCriteria(
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children);


}