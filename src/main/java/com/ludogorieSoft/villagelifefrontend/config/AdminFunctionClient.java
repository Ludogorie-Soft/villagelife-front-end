package com.ludogorieSoft.villagelifefrontend.config;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageVideoDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "villagelife-api-admin-function", url = "${backend.url}/admins/functions")
public interface AdminFunctionClient {

    @DeleteMapping("/village-delete/{villageId}")
    void deleteVillageById(@PathVariable("villageId") Long villageId, @RequestHeader("Authorization") String token);

    @PostMapping("/approve/{villageId}")
    void changeVillageStatus(@RequestParam("villageId") Long villageId,
                             @RequestParam("answerDate") String answerDate, @RequestHeader("Authorization") String token);

    @GetMapping("/toApprove")
    ResponseEntity<List<VillageResponse>> getUnapprovedVillageResponses(@RequestHeader("Authorization") String token);

    @PostMapping("/reject/{villageId}")
    void rejectVillageResponse(@RequestParam("villageId") Long villageId,
                               @RequestParam("answerDate") String answerDate, @RequestHeader("Authorization") String token);

    @GetMapping("/info/{villageId}")
    VillageInfo getVillageInfoById(@RequestParam("villageId") Long villageId,
                                   @RequestParam("answerDate") String answerDate, @RequestParam boolean status,
                                   @RequestHeader("Authorization") String token);

    @GetMapping("/getRejected")
    ResponseEntity<List<VillageResponse>> getVillagesWithRejectedResponses(@RequestHeader("Authorization") String token);

    @GetMapping("/toLatin")
    public ResponseEntity<String> translateVillagesNamesToLatin(@RequestHeader("Authorization") String token);

    @GetMapping("/videos/{villageId}")
    List<VillageVideoDTO> getAllVideos(@PathVariable(value = "villageId") Long villageId, @RequestHeader("Authorization") String token);

    @PostMapping("/video")
    void saveVideos(@RequestParam("villageId") Long villageId, @RequestParam("videoUrl") List<String> villageVideoDTOS, @RequestHeader("Authorization") String token);

    @GetMapping("/deleted-videos/{villageId}")
    List<VillageVideoDTO> getAllDeletedVideosByVillageId(@PathVariable(value = "villageId") Long villageId, @RequestHeader("Authorization") String token);

    @DeleteMapping("/video-delete/{videoId}")
    String deleteVideoByVideoId(@PathVariable("videoId") Long videoId, @RequestHeader("Authorization") String token);

    @PutMapping("/resume-video/{videoId}")
    String resumeVideoByVideoId(@PathVariable(value = "videoId") Long videoId, @RequestHeader("Authorization") String token);

    @PutMapping("/reject-video/{videoId}")
    public String rejectVideoByVideoId(@PathVariable(value = "videoId") Long videoId, @RequestHeader("Authorization") String token);
}
