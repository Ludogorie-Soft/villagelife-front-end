package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-filter", url = "http://localhost:8088/api/v1/filter")
public interface FilterClient {


    @GetMapping("/byName")
    List<VillageDTO> getVillageByName(@RequestParam("name") String name);

    @GetMapping("/byRegion")
    List<VillageDTO> getVillageByRegion(@RequestParam("region") String region);

    @GetMapping("/searchAll")
    List<VillageDTO> getVillageByNameAndRegion(@RequestParam String region, @RequestParam String keyword);

    @GetMapping("/searchVillages")
    List<VillageDTO> searchVillagesByCriteria(
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children);


    @GetMapping("/searchVillagesByObjectAndChildren")
    List<VillageDTO> searchVillagesByObjectAndChildren(
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("children") String children);

    @GetMapping("/searchVillagesByLivingConditionAndChildren")
    List<VillageDTO> searchVillagesByLivingConditionAndChildren(
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children);


    @GetMapping("/searchVillagesByObjectAndLivingCondition")
   List<VillageDTO> searchVillagesByObjectAndLivingCondition(
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);
}