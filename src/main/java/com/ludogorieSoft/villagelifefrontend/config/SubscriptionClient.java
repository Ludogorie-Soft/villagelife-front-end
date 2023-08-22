package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-subscription",url = "http://localhost:8088/api/v1/subscriptions")
public interface SubscriptionClient {
    @PostMapping
    ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO);
    @GetMapping
    List<SubscriptionDTO> getAllSubscriptions(@RequestHeader("Authorization") String token);
}
