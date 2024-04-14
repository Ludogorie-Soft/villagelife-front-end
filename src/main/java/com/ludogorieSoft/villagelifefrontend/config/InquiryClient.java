package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-inquiry",url = "${backend.url}/inquiries")
public interface InquiryClient {
    @PostMapping
    InquiryDTO createInquiry(@Valid @RequestBody InquiryDTO inquiryDTO);
    @GetMapping
    List<InquiryDTO> getAllInquiries(@RequestHeader("Authorization") String token);
}
