package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.advanced.AdvancedSearchForm;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "villagelife-api-add-advanced-search-form-result",url = "http://localhost:8088/api/v1/advancedSearchForm")
public interface AdvancedSearchFormClient {
    @PostMapping
    AdvancedSearchForm createAdvancedSearchFormResult(AdvancedSearchForm advancedSearchForm);

}

