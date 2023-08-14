package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "villagelife-api-messages",url = "${backend.url}/messages")
public interface MessageClient {
    @PostMapping
    MessageDTO createMessage(MessageDTO messageDTO);
}
