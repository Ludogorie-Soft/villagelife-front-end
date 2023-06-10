package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AddVillageFormClient;
import com.ludogorieSoft.villagelifefrontend.config.GroundCategoryClient;
import com.ludogorieSoft.villagelifefrontend.config.PopulationClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AddVillageFormResult;
import com.ludogorieSoft.villagelifefrontend.dtos.GroundCategoryDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PopulationDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
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
    String test(Model model)
    {
        PopulationDTO populationDTO = populationClient.getPopulationById(1L);
        model.addAttribute("population", populationDTO);
        return "/test/test";
    }

    //@GetMapping("/create")
    //String createVillage(Model model){
    //    VillageDTO villageDTO = new VillageDTO();
    //    System.out.println(1);
    //    villageDTO.setPopulationDTO(new PopulationDTO());
    //    System.out.println(2);
    //    model.addAttribute("village", villageDTO);
    //    System.out.println(3);
    //    return "/add-village";
    //}
    //@PostMapping("/submit")
    //String saveVillage(VillageDTO villageDTO){
    //    System.out.println(4);
    //    //populationClient.getPopulationById(villageDTO.)
    //    System.out.println(5);
    //    populationClient.createPopulation(villageDTO.getPopulationDTO());
    //    System.out.println(6);
    //    villageDTO.setPopulationDTO(populationClient.getPopulationById(villageDTO.getPopulationDTO().getId()));
    //    System.out.println(7);
    //    villageClient.createVillage(villageDTO);
    //    System.out.println(8);
    //    return "test/test";
    //}


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
