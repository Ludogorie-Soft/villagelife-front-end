package com.ludogoriesoft.villagelifefrontend.dtos;

import com.ludogoriesoft.villagelifefrontend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorRequest  {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String mobile;
    private LocalDateTime createdAt;
    private final boolean ENABLED = true;
    private Role role;

}
