package com.ludogoriesoft.villagelifefrontend.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "villagelife-api-village-images",url = "http://localhost:8088/api/v1/villageImages")
public interface VillageImageClient {
    @GetMapping("/getImageBytesFromMultipartFile")
    List<byte[]> getImageBytesFromMultipartFile(@RequestBody List<MultipartFile> images);
}
