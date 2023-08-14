package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.SubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-subscription",url = "${backend.url}/subscriptions")
public interface SubscriptionClient {
    @PostMapping
    ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO);
    @GetMapping
    List<SubscriptionDTO> getAllSubscriptions(@RequestHeader("Authorization") String token);

    @GetMapping("/check-email")
    boolean emailExists(@RequestParam String email);
}
