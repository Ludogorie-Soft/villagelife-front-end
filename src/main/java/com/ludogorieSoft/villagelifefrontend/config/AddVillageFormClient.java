package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.AddVillageFormResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "villagelife-api-add-village-form-result",url = "http://localhost:8088/api/v1/addVillageForm")
public interface AddVillageFormClient {
    @PostMapping
    AddVillageFormResult createAddVillageForResult(AddVillageFormResult addVillageFormResult);
}
