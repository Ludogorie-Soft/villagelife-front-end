package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageImageDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-village-images", url = "${backend.url}/villageImages")
public interface VillageImageClient {
    @GetMapping("/village/{villageId}/images")
    ResponseEntity<List<String>> getAllImagesForVillage(@PathVariable Long villageId, @RequestParam boolean status, @RequestParam String date);

    /*@GetMapping("/all")
    ResponseEntity<List<VillageDTO>> getAllVillageDTOsWithImages();*/

    @GetMapping("/approved/{page}/{elements}")
    ResponseEntity<List<VillageDTO>> getAllApprovedVillageDTOsWithImages(@PathVariable("page") int page, @PathVariable("elements") int elements);
    @GetMapping("/approved/pagesCount/{page}/{elements}")
    Integer getAllApprovedVillageDTOsWithImagesPageCount(@PathVariable("page") int page, @PathVariable("elements") int elements);
    @PostMapping("/admin-upload")
    List<byte[]> adminUploadImages(@RequestParam("villageId") Long villageId, @RequestBody List<byte[]> imageBytesList, @RequestHeader("Authorization") String token);

    @GetMapping("/with-base64/village/{villageId}")
    List<VillageImageDTO> getNotDeletedVillageImageDTOsByVillageId(@PathVariable("villageId") Long villageId, @RequestHeader("Authorization") String token);

    @PutMapping("/reject/{id}")
    VillageImageDTO rejectVillageImage(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    VillageImageDTO getVillageImageById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    String deleteImageFileById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
    @GetMapping("/deleted/with-base64/village/{villageId}")
    List<VillageImageDTO> getDeletedVillageImageDTOsByVillageId(@PathVariable("villageId") Long villageId, @RequestHeader("Authorization") String token);

    @PutMapping("/resume/{id}")
    VillageImageDTO resumeImageVillageById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    VillageImageDTO updateVillageImage(@PathVariable("id") Long id, @Valid @RequestBody VillageImageDTO villageImageDTO, @RequestHeader("Authorization") String token);

    @GetMapping("/upload-images")
    String uploadImages();
}
