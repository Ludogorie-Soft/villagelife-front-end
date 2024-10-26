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
public class AuthenticationRequest {
    @Length(min=3, max = 10, message = "register.request.validations.username.length")
    private String username;

    @Length(min = 8, max = 255, message = "register.request.validations.password.length")
    private String password;
}
