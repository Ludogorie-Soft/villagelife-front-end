package com.ludogorieSoft.villagelifefrontend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.enums.Children;
import com.ludogorieSoft.villagelifefrontend.enums.Foreigners;
import com.ludogorieSoft.villagelifefrontend.enums.NumberOfPopulation;
import com.ludogorieSoft.villagelifefrontend.enums.Residents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopulationDTO {

    private Long id;
    private Long villageId;
    private int populationCount;
    private NumberOfPopulation numberOfPopulation;
    private Residents residents;
    private Children children;
    private Foreigners foreigners;
    private Boolean status;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateUpload;
}
