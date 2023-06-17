package com.ludogoriesoft.villagelifefrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthnicityVillageDTO {

    private Long id;
    private Long villageId;
    private Long ethnicityId;
}
