package com.ludogoriesoft.villagelifefrontend.dtos;

import com.ludogoriesoft.villagelifefrontend.enums.Consents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VillageLivingConditionDTO {

    private Long id;
    private Long villageId;
    private Long livingConditionId;
    private Consents consents;
}
