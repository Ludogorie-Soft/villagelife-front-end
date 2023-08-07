package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "villagelife-api-admin", url = "http://localhost:8088/api/v1/admins")
public interface AdminClient {
    @GetMapping
    ResponseEntity<List<AdministratorDTO>> getAllAdministrators(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PostMapping
    ResponseEntity<AdministratorDTO> createAdministrator(@Valid @RequestBody AdministratorRequest administratorRequest,
                                                         @RequestHeader("Authorization") String token);

    @PutMapping("/update/{id}")
    ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long id,
                                                         AdministratorRequest administratorRequest,
                                                         @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteAdministratorById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/username/{username}")
    AdministratorRequest getAdministratorByUsername(@PathVariable("username") String username);

    @GetMapping("village")
    List<VillageResponse> getAllVillages(@RequestHeader("Authorization") String token);

    @DeleteMapping("/village-delete/{villageId}")
    ResponseEntity<String> deleteVillageById(@PathVariable("villageId") Long villageId, @RequestHeader("Authorization") String token);

    @PostMapping("/approve/{villageId}")
    ResponseEntity<String> changeVillageStatus(@RequestParam("villageId") Long villageId,
                                               @RequestParam("answerDate") String answerDate, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/update")
    ResponseEntity<List<VillageResponse>> findUnapprovedVillageResponseByVillageId(@RequestHeader("Authorization") String token);

    @PostMapping("/reject/{villageId}")
    void rejectVillageResponse(@RequestParam("villageId") Long villageId,
                               @RequestParam("answerDate") String answerDate, @RequestHeader("Authorization") String token);

    @GetMapping("/info/{villageId}")
    public VillageInfo getVillageInfoById(@RequestParam("villageId") Long villageId,
                                          @RequestParam("answerDate") String answerDate,@RequestParam boolean status,
                                          @RequestHeader("Authorization") String token);

}
