package com.ludogorieSoft.villagelifefrontend.dtos;

import com.ludogorieSoft.villagelifefrontend.enums.Distance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectVillageDTO {

    private Long id;
    private Long villageId;
    private Long objectAroundVillageId;
    private Distance distance;

}
