package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/villages")
public class VillageController {
    private final RegionClient regionClient;
    private final VillageClient villageClient;
    private final AddVillageFormClient addVillageFormClient;
    private final GroundCategoryClient groundCategoryClient;
    private final VillageEthnicityClient villageEthnicityClient;
    private final VillageAnswerQuestionClient villageAnswerQuestionClient;
    private final VillageLivingConditionClient villageLivingConditionClient;
    private final VillagePopulationAssertionClient villagePopulationAssertionClient;
    private final EthnicityClient ethnicityClient;
    private final PopulationClient populationClient;
    private final QuestionClient questionClient;
    private final ObjectAroundVillageClient objectAroundVillageClient;
    private final ObjectVillageClient objectVillageClient;
    private final PopulatedAssertionClient populatedAssertionClient;
    private final LivingConditionClient livingConditionClient;

    @GetMapping
    String getVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute("villages", villages);
        return "/test/testAllVillages";
    }

    @GetMapping("/home-page")
    public String homePage(Model model) {
        List<VillageDTO> villageList = villageClient.getAllVillages();
        model.addAttribute("villages", villageList);
        return "HomePage";
    }

    @GetMapping("/show/{id}")
    public String getAllTablesByVillageId(@PathVariable(name = "id") Long id, Model model) {
        List<ObjectAroundVillageDTO> objectAroundVillage = objectAroundVillageClient.getAllObjectsAroundVillage();
        model.addAttribute("objectAroundVillage", objectAroundVillage);
        List<ObjectVillageDTO> objectVillage =objectVillageClient.getObjectVillageByVillageID(id);
        model.addAttribute("objectVillage", objectVillage);
        EthnicityVillageDTO ethnicityVillage=villageEthnicityClient.getEthnicityVillageByVillageId(id);
        model.addAttribute("ethnicityVillage", ethnicityVillage);
        EthnicityDTO ethnicity=ethnicityClient.getEthnicityById(ethnicityVillage.getEthnicityId());
        model.addAttribute("ethnicity", ethnicity);
        PopulationDTO population=populationClient.getPopulationById(id);
        model.addAttribute("population", population);
        VillageDTO villageList = villageClient.getVillageById(id);
        model.addAttribute("villages", villageList);
        List<QuestionDTO> question = questionClient.getAllQuestions();
        List<VillageAnswerQuestionDTO> villageAnswerQuestion = villageAnswerQuestionClient.getVillageAnswerQuestionByVillageId(id);
        model.addAttribute("question", question);
        model.addAttribute("villageAnswerQuestion", villageAnswerQuestion);
        List<LivingConditionDTO> livingCondition = livingConditionClient.getAllLivingConditions();
        List<VillageLivingConditionDTO> villageLivingCondition = villageLivingConditionClient.getVillageLivingConditionsByVillageId(id);
        List<PopulatedAssertionDTO> villagePopulation = populatedAssertionClient.getAllPopulatedAssertion();
        List<VillagePopulationAssertionDTO> villagePopulationAssertion = villagePopulationAssertionClient.getVillagePopulationAssertionByVillageId(id);
        model.addAttribute("villagePopulationAssertion", villagePopulationAssertion);
        model.addAttribute("villagePopulation", villagePopulation);
        model.addAttribute("villageLivingCondition", villageLivingCondition);
        model.addAttribute("livingCondition", livingCondition);
        return "ShowVillageById";
    }

    @GetMapping("/create")
    public String showCreateVillageForm(Model model) {
        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();

        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

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

        model.addAttribute("addVillageFormResult", addVillageFormResult);
        return "add-village";
    }
    @PostMapping("/save")
    public String saveVillage(@ModelAttribute("addVillageFormResult") AddVillageFormResult addVillageFormResult) {
        addVillageFormClient.createAddVillageForResult(addVillageFormResult);
        return "redirect:/villages/home-page";
    }

    @GetMapping("/test")
    String test(Model model) {
        PopulationDTO populationDTO = populationClient.getPopulationById(1L);
        model.addAttribute("population", populationDTO);
        return "/test/test";
    }


}
