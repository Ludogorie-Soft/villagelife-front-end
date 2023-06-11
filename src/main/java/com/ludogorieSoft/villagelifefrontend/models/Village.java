package com.ludogorieSoft.villagelifefrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Village {
    private Long id;
    private ImageIcon image;
    private String name;
    private Population population;
    private LocalDateTime dateUpload;
    private boolean status;
    private Administrator admin;
    private LocalDateTime dateApproved;

}