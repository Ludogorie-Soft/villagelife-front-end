package com.ludogorieSoft.villagelifefrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VillageImageResponse {
    private VillageDTO villageDTO;
    private List<String> images;
    private Boolean status;
    private LocalDateTime dateUpload;
}
