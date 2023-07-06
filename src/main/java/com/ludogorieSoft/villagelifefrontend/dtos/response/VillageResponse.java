package com.ludogorieSoft.villagelifefrontend.dtos.response;

import com.ludogorieSoft.villagelifefrontend.models.Administrator;
import com.ludogorieSoft.villagelifefrontend.models.Population;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageResponse {

    private Long id;

    private String name;

    private Population population;

    private LocalDateTime dateUpload;

    private boolean status;

    private Administrator admin;

    private LocalDateTime dateApproved;

}
