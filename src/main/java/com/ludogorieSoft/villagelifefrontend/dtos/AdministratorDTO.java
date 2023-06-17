package com.ludogoriesoft.villagelifefrontend.dtos;

import com.ludogoriesoft.villagelifefrontend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDTO {
    private Long id;
    private String fullName;
    private String email;
    private String username;

    private String newPassword;
    private String mobile;
    private LocalDateTime createdAt;
    private boolean enabled;
    private Role role;

}
