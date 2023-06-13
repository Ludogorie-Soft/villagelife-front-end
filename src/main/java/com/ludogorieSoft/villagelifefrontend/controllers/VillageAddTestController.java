package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.LivingConditionClient;
import com.ludogoriesoft.villagelifefrontend.config.VillageClient;
import com.ludogoriesoft.villagelifefrontend.config.VillageLivingConditionClient;
import com.ludogoriesoft.villagelifefrontend.config.VillagePopulationAssertionClient;
import com.ludogoriesoft.villagelifefrontend.dtos.LivingConditionDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageLivingConditionDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.VillagePopulationAssertionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("villages")
@AllArgsConstructor
public class VillageAddTestController {

    private final VillageClient villageClient;
    private final LivingConditionClient livingConditionClient;
    private final VillageLivingConditionClient villageLivingConditionClient;
    private final VillagePopulationAssertionClient villagePopulationAssertionClient;


    @GetMapping("/home-page")
    public String homePage(Model model) {
        List<VillageDTO> villageList = villageClient.getAllVillages();
        model.addAttribute("villages", villageList);
        return "HomePage";
    }

    @GetMapping("/show/{id}")
    public String getAllTablesByVillageId(@PathVariable(name = "id") Long id, Model model) {
        List<LivingConditionDTO> livingCondition=livingConditionClient.getAllLivingConditions();
        List<VillageLivingConditionDTO> villageLivingCondition = villageLivingConditionClient.getVillageLivingConditionsByVillageId(id);
        List<VillagePopulationAssertionDTO> villagePopulationAssertion = villagePopulationAssertionClient.getVillagePopulationAssertionByVillageId(id);
        model.addAttribute("villagePopulationAssertion", villagePopulationAssertion);
        model.addAttribute("villageLivingCondition", villageLivingCondition);
        model.addAttribute("livingCondition", livingCondition);
        return "ShowVillageById";
    }


}


