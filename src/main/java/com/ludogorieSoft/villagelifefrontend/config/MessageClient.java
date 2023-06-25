package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "villagelife-api-messages",url = "http://localhost:8088/api/v1/messages")
public interface MessageClient {
    @PostMapping
    MessageDTO createMessage(MessageDTO messageDTO);
}
