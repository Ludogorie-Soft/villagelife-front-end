package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.ShowVillagesHomePage;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("village")
@AllArgsConstructor
public class VillageAddTestController {
    private final ShowVillagesHomePage showVillagesHomePage;


    @GetMapping("/home-page")
    //http://localhost:8087/village/home-page
    public String homePage(Model model) {
        List<VillageDTO> villageList = showVillagesHomePage.getAllVillages();
        model.addAttribute("villages", villageList);
        return "HomePage";
    }


}
