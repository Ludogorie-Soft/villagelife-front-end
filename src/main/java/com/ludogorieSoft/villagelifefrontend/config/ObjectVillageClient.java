package com.ludogoriesoft.villagelifefrontend.config;

import com.ludogoriesoft.villagelifefrontend.dtos.ObjectVillageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "villagelife-api-object-village-client",url = "http://localhost:8088/api/v1/objectVillages")

public interface ObjectVillageClient {
    @GetMapping
   List<ObjectVillageDTO> getAllObjectVillages();

    @GetMapping("/village/{id}")
    public List<ObjectVillageDTO> getObjectVillageByVillageID(@PathVariable("id") Long id) ;

    @PostMapping
     void createObjectVillage( @RequestBody ObjectVillageDTO objectVillageDTO);

}
