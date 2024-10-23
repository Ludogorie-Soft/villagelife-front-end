package com.ludogorieSoft.villagelifefrontend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private Long id;
    private VillageDTO villageDTO;
    private PropertyUserDTO propertyUserDTO;
    @NotNull(message = "Property type is required.")
    private PropertyType propertyType;
    private PropertyTransferType propertyTransferType;

    @NotNull(message = "This field is required")
    @Min(value = 0, message = "Must be greater than or equal to 0")
    private BigDecimal price;

    @Size(min = 10, message = "Phone number should be at least 10 characters long!")
    private String phoneNumber;

//    @NotNull(message = "This field is required")
//    @Min(value = 0, message = "Must be greater than or equal to 0")
    private Double buildUpArea;

    @NotNull(message = "This field is required")
    @Min(value = 0, message = "Must be greater than or equal to 0")
    private Double yardArea;

//    @NotNull(message = "This field is required")
//    @Min(value = 0, message = "Must be greater than or equal to 0")
    private int roomsCount;

//    @NotNull(message = "This field is required")
//    @Min(value = 0, message = "Must be greater than or equal to 0")
    private int bathroomsCount;
    private List<String> heating;
    private List<PropertyImageDTO> images;
    private String imageUrl;
    private ConstructionType constructionType;
    private String constructionYear;
    private String extras;
    @NotBlank(message = "Description is required and cannot be blank.")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters long.")
    private String description;
    @NotBlank(message = "Address is required and cannot be blank.")
    private String address;
    private PropertyStatsDTO propertyStatsDTO;
    @NotNull(message = "Ownership type is required.")
    private OwnershipType ownershipType;
    @NotNull(message = "Property condition is required.")
    private PropertyCondition propertyCondition;
    private byte[] mainImageBytes;
    private String heatingText;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deactivatedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deletedAt;

}
