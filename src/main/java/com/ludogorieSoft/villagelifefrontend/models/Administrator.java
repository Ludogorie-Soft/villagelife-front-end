package com.ludogoriesoft.villagelifefrontend.models;

import com.ludogoriesoft.villagelifefrontend.enums.Role;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Administrator{

    private Long id;

    private String fullName;

    private String email;

    private String username;

    private String password;

    private String mobile;

    private LocalDateTime createdAt;
    private static final  boolean ENABLED = true;

    private Role role;

}
