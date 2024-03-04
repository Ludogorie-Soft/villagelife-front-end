package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogorieSoft.villagelifefrontend.utils.PageableResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-filter", url = "${backend.url}/filter")
public interface FilterClient {

    @GetMapping("/{page}")
    List<VillageDTO> getAllApprovedVillages(@PathVariable("page") int page, @RequestParam(required = false) String sort);

    @GetMapping("/{page}/elementsCount")
    Long getAllApprovedVillagesElementsCount(@PathVariable("page") int page);

    @GetMapping("/byName/{page}")
    List<VillageDTO> getVillageByName(@PathVariable("page") int page, @RequestParam("name") String name, @RequestParam(required = false) String sort);

    @GetMapping("/byName/{page}/elementsCount")
    Long getVillageByNameElementsCount(@PathVariable("page") int page, @RequestParam("name") String name);

    @GetMapping("/byRegion/{page}")
    List<VillageDTO> getVillageByRegion(@PathVariable("page") int page, @RequestParam("region") String region, @RequestParam(required = false) String sort);

    @GetMapping("/byRegion/{page}/elementsCount")
    Long getVillageByRegionElementsCount(@PathVariable("page") int page, @RequestParam("region") String region);

    @GetMapping("/searchAll/{page}")
    List<VillageDTO> getVillageByNameAndRegion(@PathVariable("page") int page, @RequestParam String region, @RequestParam String keyword, @RequestParam(required = false) String sort);

    @GetMapping("/searchAll/{page}/elementsCount")
    Long getVillageByNameAndRegionElementsCount(@PathVariable("page") int page, @RequestParam String region, @RequestParam String keyword);


    @GetMapping("/searchVillages/{page}")
    PageableResponse<VillageDTO> searchVillagesByCriteria(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "name", required = false) String villageName,
            @RequestParam(value = "objectAroundVillageDTOS", required = false) List<String> objectAroundVillageDTOS,
            @RequestParam(value = "livingConditionDTOS", required = false) List<String> livingConditionDTOS,
            @RequestParam(value = "children", required = false) String children,
            Pageable pageable
    );



    @GetMapping("/searchVillages/{page}/elementsCount")
    Long searchVillagesByCriteriaElementsCount(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam("children") String children
    );

    @GetMapping("/searchVillagesByObjectAndChildren/{page}")
    List<VillageDTO> searchVillagesByObjectAndChildren(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("children") String children,
            @RequestParam(required = false) String sort);

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
            @RequestParam("children") String children,
            @RequestParam(required = false) String sort);

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
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS,
            @RequestParam(required = false) String sort);

    @GetMapping("/searchVillagesByObjectAndLivingCondition/{page}/elementsCount")
    Long searchVillagesByObjectAndLivingConditionElementsCount(
            @PathVariable("page") int page,
            @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS,
            @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);

    @GetMapping("/searchVillagesByChildrenCount/{page}")
    List<VillageDTO> searchVillagesByChildrenCount(@PathVariable("page") int page, @RequestParam("children") String children, @RequestParam(required = false) String sort);

    @GetMapping("/searchVillagesByChildrenCount/{page}/elementsCount")
    Long searchVillagesByChildrenCountElementsCount(@PathVariable("page") int page, @RequestParam("children") String children);

    @GetMapping("/searchVillagesByObject/{page}")
    List<VillageDTO> searchVillagesByObject(@PathVariable("page") int page, @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS, @RequestParam(required = false) String sort);

    @GetMapping("/searchVillagesByObject/{page}/elementsCount")
    Long searchVillagesByObjectElementsCount(
            @PathVariable("page") int page, @RequestParam("objectAroundVillageDTOS") List<String> objectAroundVillageDTOS);

    @GetMapping("/searchVillagesByLivingCondition/{page}")
    List<VillageDTO> searchVillagesByLivingCondition(@PathVariable("page") int page, @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS, @RequestParam(required = false) String sort);

    @GetMapping("/searchVillagesByLivingCondition/{page}/elementsCount")
    Long searchVillagesByLivingConditionElementsCount(@PathVariable("page") int page, @RequestParam("livingConditionDTOS") List<String> livingConditionDTOS);
}
