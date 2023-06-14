package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.EthnicityDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.EthnicityVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@FeignClient(name = "villagelife-api-ethnicities",url = "http://localhost:8088/api/v1/ethnicities")
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
