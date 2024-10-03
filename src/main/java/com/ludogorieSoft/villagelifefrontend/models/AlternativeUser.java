package com.ludogorieSoft.villagelifefrontend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ludogorieSoft.villagelifefrontend.dtos.BusinessCardDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.UserSearchDataDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VerificationTokenDTO;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AlternativeUser {

    private Long id;

    private String fullName;

    private String email;

    private String username;

    private String password;

    private String mobile;

    private List<VerificationTokenDTO> verificationTokenDTOs;

    private UserSearchDataDTO userSearchDataDTO;

    private String jobTitle;

    private BusinessCardDTO businessCardDTO;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deletedAt;

    private boolean enabled = true;

    private Role role;

}
