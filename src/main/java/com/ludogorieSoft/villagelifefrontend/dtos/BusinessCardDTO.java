package com.ludogorieSoft.villagelifefrontend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  BusinessCardDTO {
    private Long id;

    @NotBlank(message = "business.card.dto.validations.name.blank")
    @Length(max = 255, message = "business.card.dto.validations.name.length")
    private String name;

    @NotBlank(message = "business.card.dto.validations.email.blank")
    @Email(message = "business.card.dto.validations.email.valid")
    @Length(max = 255, message = "business.card.dto.validations.email.length")
    private String email;

    @NotBlank(message = "business.card.dto.validations.phone.number.blank")
    @Length(min = 7, max = 25, message = "business.card.dto.validations.phone.number.length")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "business.card.dto.validations.phone.number.invalid")
    private String phoneNumber;

    @NotBlank(message = "business.card.dto.validations.address.blank")
    @Length(max = 255, message = "business.card.dto.validations.address.length")
    private String address;

    @Length(max = 255, message = "business.card.dto.validations.website.link.length")
    private String websiteLink;

    @Min(value = 1, message = "business.card.dto.validations.number.of.employees.min")
    @Max(value = 2000000000, message = "business.card.dto.validations.number.of.employees.max")
    private int numberOfEmployees;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deletedAt;
}
