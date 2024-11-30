package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.UserSearchDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "villagelife-api-user-search-data", url = "${backend.url}/user-search-data")
public interface UserSearchDataClient {
    @PostMapping
    UserSearchDataDTO createUserSearchData(@RequestHeader("Authorization") String token, UserSearchDataDTO userSearchDataDTO);

    @GetMapping("/get-all-for-user")
    List<UserSearchDataDTO> getAllUserSearchDataDTOsForUser(@RequestHeader("Authorization") String token, @RequestParam(value = "alternativeUserId", required = false) Long id);

    @DeleteMapping("/{id}")
    String softDeleteUserSearchDataById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);
}
