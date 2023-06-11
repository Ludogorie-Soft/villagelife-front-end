package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.EthnicityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@FeignClient(name = "villagelife-api-ethnicities",url = "http://localhost:8088/api/v1/ethnicities")
public interface EthnicityClient {
    @GetMapping
    List<EthnicityDTO> getAllEthnicities();
    @GetMapping("/{id}")
    EthnicityDTO getEthnicityById(@PathVariable("id") Long id);
    //@PostMapping
    //EthnicityDTO createEthnicity(EthnicityDTO ethnicityDTO, UriComponentsBuilder uriComponentsBuilder);
    @PutMapping("/{id}")
    EthnicityDTO updateEthnicity(@PathVariable("id") Long id, EthnicityDTO ethnicityDTO);
    @DeleteMapping("/{id}")
    String deleteEthnicityById(@PathVariable("id") Long id);
}
