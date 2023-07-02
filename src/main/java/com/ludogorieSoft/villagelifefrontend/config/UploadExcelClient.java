package com.ludogoriesoft.villagelifefrontend.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "villagelife-api-uploadExcel", url = "http://localhost:8088/api/v1/upload")
public interface UploadExcelClient {

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long uploadFile(@RequestPart("file") MultipartFile file);

}

