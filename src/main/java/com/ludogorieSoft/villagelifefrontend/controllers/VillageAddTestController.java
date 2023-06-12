package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.VillageClient;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("village")
@AllArgsConstructor
public class VillageAddTestController {

    private final VillageClient villageClient;


    @GetMapping("/home-page")
    //http://localhost:8087/village/home-page
    public String homePage(Model model) {
        List<VillageDTO> villageList = villageClient.getAllVillages();
        model.addAttribute("villages", villageList);
        return "HomePage";
    }

    @GetMapping("/show/{id}")
    //http://localhost:8087/village/show/{id}
    public String homePage(@PathVariable(name = "id")Long id, Model model) {
       VillageDTO village = villageClient.getVillageById(id);
        model.addAttribute("villages", village);
        return "ShowVillageById";
    }


}
