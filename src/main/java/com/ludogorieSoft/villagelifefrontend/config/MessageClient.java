package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "villagelife-api-messages",url = "${backend.url}/messages")
public interface MessageClient {
    @PostMapping
    MessageDTO createMessage(MessageDTO messageDTO);
    @GetMapping
    List<MessageDTO> getAllMessages(@RequestHeader("Authorization") String token);
}
