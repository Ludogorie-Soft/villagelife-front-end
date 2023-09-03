package com.ludogorieSoft.villagelifefrontend.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorRequest  {
    private Long id;

    @NotBlank(message = "Full name cannot be empty!")
    @Length(min = 2, message = "Full name should be at least than 2 characters long!")
    private String fullName;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Please enter a valid email address!")
    private String email;

    @NotBlank(message = "Username cannot be empty!")
    @Length(min=3, max = 10, message = "Username should be from 3 to 10 characters long!")
    private String username;

    private String password;

    @Length(min = 10, message = "Phone number should be at least 10 numbers long!")
    private String mobile;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    private boolean enabled = true;
    private Role role;

}
