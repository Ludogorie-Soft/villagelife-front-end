package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-admin", url = "http://localhost:8088/api/v1/admins")
public interface AdminClient {
    @GetMapping
    ResponseEntity<List<AdministratorDTO>> getAllAdministrators(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long id,@RequestHeader("Authorization") String token);

    @PostMapping
    ResponseEntity<AdministratorDTO> createAdministrator(@Valid @RequestBody AdministratorRequest administratorRequest,
                                                         @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long id,
                                                         @RequestBody AdministratorRequest administratorRequest,
                                                         @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAdministratorById(@PathVariable("id") Long id,@RequestHeader("Authorization") String token);

    @GetMapping("/username/{username}")
    AdministratorRequest getAdministratorByUsername(@PathVariable("username") String username);
}
