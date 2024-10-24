package com.ludogorieSoft.villagelifefrontend.dtos.request;

import com.ludogorieSoft.villagelifefrontend.dtos.BusinessCardDTO;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Length(min = 2, max = 255, message = "register.request.validations.full.name.length")
    private String fullName;

    @Email(message = "register.request.validations.email.valid")
    @Length(min = 3, max = 255, message = "register.request.validations.email.length")
    private String email;

    @Length(min=3, max = 10, message = "register.request.validations.username.length")
    private String username;

    @Length(min = 8, max = 255, message = "register.request.validations.password.length")
    private String password;

    @Length(min = 10, max = 255, message = "register.request.validations.phone.number.length")
    private String mobile;

    private Role role;

    @Length(max = 255, message = "register.request.validations.job.title.length")
    private String jobTitle;

    private BusinessCardDTO businessCardDTO;
}
