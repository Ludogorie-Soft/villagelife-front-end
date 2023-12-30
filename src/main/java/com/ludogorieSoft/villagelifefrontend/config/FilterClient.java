package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-filter", url = "${backend.url}/filter")
public interface FilterClient {

    @GetMapping("/{page}")
    List<VillageDTO> getAllApprovedVillages(@PathVariable("page") int page);
    @GetMapping("/{page}/elementsCount")
    Long getAllApprovedVillagesElementsCount(@PathVariable("page") int page);

    @GetMapping("/byName/{page}")
    List<VillageDTO> getVillageByName(@PathVariable("page") int page, @RequestParam("name") String name);
    @GetMapping("/byName/{page}/elementsCount")
    Long getVillageByNameElementsCount(@PathVariable("page") int page, @RequestParam("name") String name);

    @GetMapping("/byRegion/{page}")
    List<VillageDTO> getVillageByRegion(@PathVariable("page") int page, @RequestParam("region") String region);
    @GetMapping("/byRegion/{page}/elementsCount")
    Long getVillageByRegionElementsCount(@PathVariable("page") int page, @RequestParam("region") String region);
    @GetMapping("/searchAll/{page}")
    List<VillageDTO> getVillageByNameAndRegion(@PathVariable("page") int page, @RequestParam String region, @RequestParam String keyword, @RequestParam(required = false) String sort);
    @GetMapping("/searchAll/{page}/elementsCount")
    Long getVillageByNameAndRegionElementsCount(@PathVariable("page") int page, @RequestParam String region, @RequestParam String keyword, @RequestParam(required = false) String sort);

    @GetMapping("/searchVillages/{page}")
    List<VillageDTO> searchVillagesByCriteria(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children,
            @RequestParam(required = false) String sort);
    @GetMapping("/searchVillages/{page}/elementsCount")
    Long searchVillagesByCriteriaElementsCount(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children,
            @RequestParam(required = false) String sort
    );

    @GetMapping("/searchVillagesByObjectAndChildren/{page}")
    List<VillageDTO> searchVillagesByObjectAndChildren(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("children") String children);
    @GetMapping("/searchVillagesByObjectAndChildren/{page}/elementsCount")
    Long searchVillagesByObjectAndChildrenElementsCount(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("children") String children
    );

    @GetMapping("/searchVillagesByLivingConditionAndChildren/{page}")
    List<VillageDTO> searchVillagesByLivingConditionAndChildren(
            @PathVariable("page") int page,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children);
    @GetMapping("/searchVillagesByLivingConditionAndChildren/{page}/elementsCount")
    Long searchVillagesByLivingConditionAndChildrenElementsCount(
            @PathVariable("page") int page,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children
    );

    @GetMapping("/searchVillagesByObjectAndLivingCondition/{page}")
    List<VillageDTO> searchVillagesByObjectAndLivingCondition(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);
    @GetMapping("/searchVillagesByObjectAndLivingCondition/{page}/elementsCount")
    Long searchVillagesByObjectAndLivingConditionElementsCount(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);

    @GetMapping("/searchVillagesByChildrenCount/{page}")
    List<VillageDTO> searchVillagesByChildrenCount(@PathVariable("page") int page, @RequestParam("children") String children);
    @GetMapping("/searchVillagesByChildrenCount/{page}/elementsCount")
    Long searchVillagesByChildrenCountElementsCount(@PathVariable("page") int page, @RequestParam("children") String children);
    @GetMapping("/searchVillagesByObject/{page}")
    List<VillageDTO> searchVillagesByObject(@PathVariable("page") int page, @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS);
    @GetMapping("/searchVillagesByObject/{page}/elementsCount")
    Long searchVillagesByObjectElementsCount(
            @PathVariable("page") int page, @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS);

    @GetMapping("/searchVillagesByLivingCondition/{page}")
    List<VillageDTO> searchVillagesByLivingCondition(@PathVariable("page") int page, @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);
    @GetMapping("/searchVillagesByLivingCondition/{page}/elementsCount")
    Long searchVillagesByLivingConditionElementsCount(@PathVariable("page") int page, @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);
}
