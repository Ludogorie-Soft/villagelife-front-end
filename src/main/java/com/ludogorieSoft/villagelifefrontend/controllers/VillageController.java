package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/villages")
public class VillageController {
    private final VillageClient villageClient;
    private final AddVillageFormClient addVillageFormClient;
    private final GroundCategoryClient groundCategoryClient;
    private final EthnicityClient ethnicityClient;
    private final PopulationClient populationClient;
    private final QuestionClient questionClient;
    private ObjectAroundVillageClient objectAroundVillageClient;
    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;
    private RegionClient regionClient;

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
    @GetMapping("/create")
    public String showCreateVillageForm(Model model) {
        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();
        addAllListsWithOptions(model);
        model.addAttribute("addVillageFormResult", addVillageFormResult);
        return "add-village";
    }


    @PostMapping("/save")
    public String saveVillage(@ModelAttribute("addVillageFormResult") AddVillageFormResult addVillageFormResult,
                              @RequestParam("images") List<MultipartFile> images) {
        List<byte[]> imageBytes = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                byte[] imageData = image.getBytes();
                imageBytes.add(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        addVillageFormResult.setImageBytes(imageBytes);
        addVillageFormClient.createAddVillageForResult(addVillageFormResult);
        return "redirect:/villages/test";
    }
    private void addAllListsWithOptions(Model model){
        List<GroundCategoryDTO> groundCategories = groundCategoryClient.getAllGroundCategories();
        model.addAttribute("groundCategories", groundCategories);

        List<EthnicityDTO> ethnicities = ethnicityClient.getAllEthnicities();
        model.addAttribute("ethnicities", ethnicities);

        List<QuestionDTO> questionDTOS = questionClient.getAllQuestions();
        model.addAttribute("questions", questionDTOS);

        List<ObjectAroundVillageDTO> objectAroundVillageDTOS = objectAroundVillageClient.getAllObjectsAroundVillage();
        model.addAttribute("objectsAroundVillage", objectAroundVillageDTOS);

        List<PopulatedAssertionDTO> populatedAssertionDTOS = populatedAssertionClient.getAllPopulatedAssertion();
        model.addAttribute("populatedAssertions", populatedAssertionDTOS);

        List<LivingConditionDTO> livingConditionDTOS = livingConditionClient.getAllLivingConditions();
        model.addAttribute("livingConditions", livingConditionDTOS);

        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
    }
}
