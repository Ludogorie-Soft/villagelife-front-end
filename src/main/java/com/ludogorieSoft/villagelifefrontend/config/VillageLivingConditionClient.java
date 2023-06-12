package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.VillageLivingConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "villagelife-api-villageLivingConditions",url = "http://localhost:8088/api/v1/villageLivingConditions")
public interface VillageLivingConditionClient {
    @GetMapping
    List<VillageLivingConditionDTO>getAllVillageLivingConditions();
}
