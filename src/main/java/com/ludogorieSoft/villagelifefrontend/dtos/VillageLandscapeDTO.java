package com.ludogorieSoft.villagelifefrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VillageLandscapeDTO {

    private Long id;
    private Long villageId;
    private Long landscapeId;
    private Boolean status;
    private LocalDateTime dateUpload;

}
