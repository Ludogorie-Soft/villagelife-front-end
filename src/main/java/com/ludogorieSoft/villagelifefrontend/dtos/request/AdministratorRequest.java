package com.ludogorieSoft.villagelifefrontend.dtos.request;

import com.ludogorieSoft.villagelifefrontend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class AdministratorRequest  {
//    private Long id;
//
//    @NotBlank(message = "Full name cannot be empty!")
//    @Length(min = 2, message = "Full name should be at least than 2 characters long!")
//    private String fullName;
//
//    @NotBlank(message = "Email cannot be empty!")
//    @Email(message = "Please enter a valid email address!")
//    private String email;
//
//    @NotBlank(message = "Username cannot be empty!")
//    @Length(min=3, max = 10, message = "Username should be from 3 to 10 characters long!")
//    private String username;
//
//    private String password;
//
//    @Length(min = 10, message = "Phone number should be at least 10 numbers long!")
//    private String mobile;
//    private LocalDateTime createdAt;
//    private boolean enabled = true;
//    private Role role;
//
//}
//