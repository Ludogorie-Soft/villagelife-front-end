package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageLivingConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "villagelife-api-villageLivingConditions",url = "${backend.url}/villageLivingConditions")
public interface VillageLivingConditionClient {
    @PostMapping
     void createVillageLivingConditions(@RequestBody VillageLivingConditionDTO villageLivingConditionsDTO);

}
