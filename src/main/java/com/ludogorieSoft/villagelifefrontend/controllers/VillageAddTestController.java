package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import lombok.AllArgsConstructor;

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
    private final EthnicityClient ethnicityClient;
    private final VillageEthnicityClient villageEthnicityClient;
    private final PopulationClient populationClient;
    private final QuestionClient questionClient;
    private final VillageAnswerQuestionClient villageAnswerQuestionClient;
    private final VillageClient villageClient;
    private final LivingConditionClient livingConditionClient;
    private final PopulatedAssertionClient populatedAssertionClient;
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


}


