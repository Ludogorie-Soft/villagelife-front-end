package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@FeignClient(name = "villagelife-api-admin",url = "http://localhost:8088/api/v1/admins")
public interface AdminClient {
    @GetMapping
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators();

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long id);

    @PostMapping
    public ResponseEntity<AdministratorDTO> createAdministrator( @RequestBody AdministratorRequest administratorRequest);

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long id,
                                                                @RequestBody AdministratorRequest administratorRequest);
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdministratorById(@PathVariable("id") Long id);
}
