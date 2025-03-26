package com.ludogorieSoft.villagelifefrontend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private Long userId;
    private String token;
    @Length(min = 8, max = 255, message = "register.request.validations.password.length")
    private String password;
    @Length(min = 8, max = 255, message = "register.request.validations.password.length")
    private String repeatedPassword;
}

