package com.ludogorieSoft.villagelifefrontend.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "villagelife-api-uploadExcel", url = "${backend.url}/upload")
public interface UploadExcelClient {

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long uploadFile(@RequestPart("file") MultipartFile file);

}
