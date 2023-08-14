package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.ObjectVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "villagelife-api-object-village-client",url = "${backend.url}/objectVillages")

public interface ObjectVillageClient {
    @GetMapping
   List<ObjectVillageDTO> getAllObjectVillages();

    @GetMapping("/village/{id}")
    public List<ObjectVillageDTO> getObjectVillageByVillageID(@PathVariable("id") Long id) ;

    @PostMapping
     void createObjectVillage( @RequestBody ObjectVillageDTO objectVillageDTO);

}
