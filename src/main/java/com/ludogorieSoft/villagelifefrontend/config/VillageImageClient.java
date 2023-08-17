package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageImageDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "villagelife-api-village-images",url = "http://localhost:8088/api/v1/villageImages")
public interface VillageImageClient {
    @GetMapping("/village/{villageId}/images")
    ResponseEntity<List<String>> getAllImagesForVillage(@PathVariable Long villageId, @RequestParam boolean status, @RequestParam String date);

    @GetMapping("/all")
    ResponseEntity<List<VillageDTO>> getAllVillageDTOsWithImages();

    @GetMapping("/approved")
    ResponseEntity<List<VillageDTO>> getAllApprovedVillageDTOsWithImages();

    @PostMapping("/admin-upload")
    List<byte[]> adminUploadImages(@RequestParam("villageId") Long villageId, @RequestBody List<byte[]> imageBytesList);

    @GetMapping("/with-base64/village/{villageId}")
    List<VillageImageDTO> getNotDeletedVillageImageDTOsByVillageId(@PathVariable("villageId") Long villageId);

    @PutMapping("/reject/{id}")
    VillageImageDTO rejectVillageImage(@PathVariable("id") Long id);

    @GetMapping("/{id}")
    VillageImageDTO getVillageImageById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    String deleteImageFileById(@PathVariable("id") Long id);
    @GetMapping("/deleted/with-base64/village/{villageId}")
    List<VillageImageDTO> getDeletedVillageImageDTOsByVillageId(@PathVariable("villageId") Long villageId);

    @PutMapping("/resume/{id}")
    VillageImageDTO resumeImageVillageById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    VillageImageDTO updateVillageImage(@PathVariable("id") Long id, @Valid @RequestBody VillageImageDTO villageImageDTO);
}
