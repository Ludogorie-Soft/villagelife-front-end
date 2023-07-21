package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.GroundCategoryClient;
import com.ludogorieSoft.villagelifefrontend.dtos.GroundCategoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/test")
@AllArgsConstructor
public class TEST {

    private final GroundCategoryClient groundCategoryClient;

    @GetMapping()
    public ResponseEntity<List<GroundCategoryDTO>> get(Model model) {
        return ResponseEntity.ok(groundCategoryClient.getAllGroundCategories());
    }
}
