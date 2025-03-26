package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogorieSoft.villagelifefrontend.utils.PageableResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "villagelife-api-filter", url = "${backend.url}/filter")
public interface FilterClient {

    @GetMapping("/searchVillages")
    PageableResponse<VillageDTO> searchVillagesByCriteria(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "name", required = false) String villageName,
            @RequestParam(value = "objectAroundVillageDTOS", required = false) List<String> objectAroundVillageDTOS,
            @RequestParam(value = "livingConditionDTOS", required = false) List<String> livingConditionDTOS,
            @RequestParam(value = "children", required = false) String children,
            Pageable pageable
    );

    @GetMapping("/searchProperties")
    PageableResponse<PropertyDTO> searchPropertiesByCriteria(
            @RequestParam(value = "propertyTypes", required = false) List<String> propertyTypes,
            @RequestParam(value = "propertyTransferType", required = false) String propertyTransferType,
            @RequestParam(value = "minBuiltUpArea", required = false) Double minBuiltUpArea,
            @RequestParam(value = "maxBuiltUpArea", required = false) Double maxBuiltUpArea,
            @RequestParam(value = "minYardArea", required = false) Double minYardArea,
            @RequestParam(value = "maxYardArea", required = false) Double maxYardArea,
            @RequestParam(value = "minRoomsCount", required = false) Short minRoomsCount,
            @RequestParam(value = "maxRoomsCount", required = false) Short maxRoomsCount,
            @RequestParam(value = "minBathroomsCount", required = false) Short minBathroomsCount,
            @RequestParam(value = "maxBathroomsCount", required = false) Short maxBathroomsCount,
            @RequestParam(value = "heating", required = false) List<String> heating,
            @RequestParam(value = "constructionTypes", required = false) List<String> constructionTypes,
            @RequestParam(value = "propertyConditions", required = false) List<String> propertyConditions,
            @RequestParam(value = "minConstructionYear", required = false) Short minConstructionYear,
            @RequestParam(value = "maxConstructionYear", required = false) Short maxConstructionYear,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "ownershipTypes", required = false) List<String> ownershipTypes,
            @RequestParam(value = "villageName", required = false) String villageName,
            @RequestParam(value = "regionName", required = false) String regionName,
            Pageable pageable
    );

    @PutMapping("/increment-seen-in-results")
    void incrementSearchPropertiesSeenInResults(
            @RequestParam(value = "propertyTypes", required = false) List<String> propertyTypes,
            @RequestParam(value = "propertyTransferType", required = false) String propertyTransferType,
            @RequestParam(value = "minBuiltUpArea", required = false) Double minBuiltUpArea,
            @RequestParam(value = "maxBuiltUpArea", required = false) Double maxBuiltUpArea,
            @RequestParam(value = "minYardArea", required = false) Double minYardArea,
            @RequestParam(value = "maxYardArea", required = false) Double maxYardArea,
            @RequestParam(value = "minRoomsCount", required = false) Short minRoomsCount,
            @RequestParam(value = "maxRoomsCount", required = false) Short maxRoomsCount,
            @RequestParam(value = "minBathroomsCount", required = false) Short minBathroomsCount,
            @RequestParam(value = "maxBathroomsCount", required = false) Short maxBathroomsCount,
            @RequestParam(value = "heating", required = false) List<String> heating,
            @RequestParam(value = "constructionTypes", required = false) List<String> constructionTypes,
            @RequestParam(value = "propertyConditions", required = false) List<String> propertyConditions,
            @RequestParam(value = "minConstructionYear", required = false) Short minConstructionYear,
            @RequestParam(value = "maxConstructionYear", required = false) Short maxConstructionYear,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "ownershipTypes", required = false) List<String> ownershipTypes,
            @RequestParam(value = "villageName", required = false) String villageName,
            @RequestParam(value = "regionName", required = false) String regionName
    );
}
