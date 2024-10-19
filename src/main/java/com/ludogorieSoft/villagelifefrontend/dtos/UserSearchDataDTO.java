package com.ludogorieSoft.villagelifefrontend.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.enums.ConstructionType;
import com.ludogorieSoft.villagelifefrontend.enums.OwnershipType;
import com.ludogorieSoft.villagelifefrontend.enums.PropertyTransferType;
import com.ludogorieSoft.villagelifefrontend.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDataDTO {

    private Long id;
    private VillageDTO villageDTO;
    private PropertyType propertyType;
    private PropertyTransferType propertyTransferType;
    private Double minBuiltUpArea;
    private Double maxBuiltUpArea;
    private Double minYardArea;
    private Double maxYardArea;
    private short minRoomsCount;
    private short maxRoomsCount;
    private short minBathroomsCount;
    private short maxBathroomsCount;
    private List<String> heating;
    private ConstructionType constructionType;
    private short minConstructionYear;
    private short maxConstructionYear;
    private String extras;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private OwnershipType ownershipType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deletedAt;
}