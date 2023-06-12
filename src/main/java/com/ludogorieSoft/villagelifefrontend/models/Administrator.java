package com.ludogoriesoft.villagelifefrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Administrator {

    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String mobile;
    private LocalDateTime createdAt;
    private final boolean enabled = true;

}
