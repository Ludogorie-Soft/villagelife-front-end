package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.AlternativeUserDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-admin", url = "${backend.url}/admins")
public interface AlternativeUserClient {
    @GetMapping
    ResponseEntity<List<AlternativeUserDTO>> getAllAdministrators(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<AlternativeUserDTO> getAdministratorById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/update/{id}")
    void updateAdministrator(@PathVariable("id") Long id,
                                                         AdministratorRequest administratorRequest,
                                                         @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteAdministratorById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
}
