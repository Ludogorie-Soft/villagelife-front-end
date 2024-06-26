package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.EthnicityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-ethnicities",url = "${backend.url}/ethnicities")
public interface EthnicityClient {
    @GetMapping
    List<EthnicityDTO> getAllEthnicities();
    @GetMapping("/{id}")
    EthnicityDTO getEthnicityById(@PathVariable("id") Long id);
    @PutMapping("/{id}")
    EthnicityDTO updateEthnicity(@PathVariable("id") Long id, EthnicityDTO ethnicityDTO);
    @DeleteMapping("/{id}")
    String deleteEthnicityById(@PathVariable("id") Long id);
}
