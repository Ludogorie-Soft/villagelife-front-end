package com.ludogorieSoft.villagelifefrontend.dtos.response;

import com.ludogorieSoft.villagelifefrontend.dtos.ObjectAroundVillageDTO;
import com.ludogorieSoft.villagelifefrontend.enums.Distance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectVillageResponse {
    private Distance distance;
    /*private String objects;*/
    private List<ObjectAroundVillageDTO> objects;
}
