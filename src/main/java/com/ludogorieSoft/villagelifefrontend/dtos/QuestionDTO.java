package com.ludogoriesoft.villagelifefrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String question;
}
