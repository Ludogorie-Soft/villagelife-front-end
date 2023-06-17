package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.AddVillageFormClient;
import com.ludogoriesoft.villagelifefrontend.config.GroundCategoryClient;
import com.ludogoriesoft.villagelifefrontend.config.PopulationClient;
import com.ludogoriesoft.villagelifefrontend.config.VillageClient;
import com.ludogoriesoft.villagelifefrontend.dtos.AddVillageFormResult;
import com.ludogoriesoft.villagelifefrontend.dtos.GroundCategoryDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.PopulationDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/villages")
public class VillageController {
    private final VillageClient villageClient;
    private final AddVillageFormClient addVillageFormClient;
    private final GroundCategoryClient groundCategoryClient;
    private final ModelMapper modelMapper;
    private final PopulationClient populationClient;
    @GetMapping
    String getVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute("villages", villages);
        return "/test/testAllVillages";
    }
    @GetMapping("/test")
    String test(Model model) {
        PopulationDTO populationDTO = populationClient.getPopulationById(1L);
        model.addAttribute("population", populationDTO);
        return "/test/test";
    }

    @GetMapping("/create")
    public String showCreateVillageForm(Model model) {
        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();
        List<GroundCategoryDTO> groundCategories = groundCategoryClient.getAllGroundCategories();
        model.addAttribute("groundCategories", groundCategories);
        model.addAttribute("addVillageFormResult", addVillageFormResult);
        return "add-village";
    }
    @PostMapping("/save")
    public String saveVillage(@ModelAttribute("addVillageFormResult") AddVillageFormResult addVillageFormResult) {
    System.out.println(addVillageFormResult.getGroundCategoryName() + "!!!!!!!!!!!!!!!!!!!!!");
        addVillageFormClient.createAddVillageForResult(addVillageFormResult);
        return "redirect:/villages/test";
    }
}
