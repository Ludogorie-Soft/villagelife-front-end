package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "villagelife-api-inquiry",url = "http://localhost:8088/api/v1/inquiries")
public interface InquiryClient {
    @PostMapping
    InquiryDTO createInquiry(@Valid @RequestBody InquiryDTO inquiryDTO);
}
