package com.ludogorieSoft.villagelifefrontend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.enums.ConstructionType;
import com.ludogorieSoft.villagelifefrontend.enums.OwnershipType;
import com.ludogorieSoft.villagelifefrontend.enums.PropertyCondition;
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
    private AlternativeUserDTO alternativeUserDTO;
    private String searchName;
    private String villageName;
    private String regionName;
    private List<PropertyType> propertyTypes;
    private PropertyTransferType propertyTransferType;
    private Double minBuiltUpArea;
    private Double maxBuiltUpArea;
    private Double minYardArea;
    private Double maxYardArea;
    private Short minRoomsCount;
    private Short maxRoomsCount;
    private Short minBathroomsCount;
    private Short maxBathroomsCount;
    private List<String> heating;
    private List<ConstructionType> constructionTypes;
    private List<PropertyCondition> propertyConditions;
    private Short minConstructionYear;
    private Short maxConstructionYear;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<OwnershipType> ownershipTypes;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deletedAt;
}
