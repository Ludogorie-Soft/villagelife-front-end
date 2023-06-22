package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.advanced.AdvancedSearchForm;
import com.ludogoriesoft.villagelifefrontend.advanced.AdvancedSearchFormValidator;
import com.ludogoriesoft.villagelifefrontend.advanced.ValidationErrorResponse;
import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import com.ludogoriesoft.villagelifefrontend.enums.Children;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final FilterClient filterClient;

    private final VillageClient villageClient;

    private ObjectAroundVillageClient objectAroundVillageClient;

    //    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;


    private final AdvancedSearchFormClient advancedSearchFormClient;


    @GetMapping("/byName")
    public String findAll(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<VillageDTO> villages;
        int resultCount;

        if (keyword != null && !keyword.isEmpty()) {
            villages = filterClient.getVillageByName(keyword);
        } else {
            villages = villageClient.getAllVillages();
        }
        resultCount = villages.size();

        model.addAttribute("villages", villages);

        if (resultCount > 0) {
            model.addAttribute("message", "Намерени резултати: " + resultCount);
        } else {
            model.addAttribute("message", "Не бяха открити резултати от вашето търсене: " + keyword);

        }

        return "SearchingForm";
    }


    @GetMapping("/advancedSearchModalForm")
    public String getPageWithModal(Model model) {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();

        List<ObjectAroundVillageDTO> objectAroundVillageDTOS = objectAroundVillageClient.getAllObjectsAroundVillage();
        model.addAttribute("objectsAroundVillage", objectAroundVillageDTOS);
        System.out.println("objectsAroundVillage" + objectAroundVillageDTOS);

//        List<PopulatedAssertionDTO> populatedAssertionDTOS = populatedAssertionClient.getAllPopulatedAssertion();
//        model.addAttribute("populatedAssertions", populatedAssertionDTOS);

        List<LivingConditionDTO> livingConditionDTOS = livingConditionClient.getAllLivingConditions();
        model.addAttribute("livingConditions", livingConditionDTOS);
        System.out.println("livingConditions" + livingConditionDTOS);

        Children[] childrenValues = Children.values();
        model.addAttribute("childrenValues", childrenValues);
        System.out.println("childrenValues" + Arrays.toString(childrenValues));

        model.addAttribute("advancedSearchForm", advancedSearchForm);

        return "page-with-modal";
    }



    @PostMapping("/search")
    public String search(@ModelAttribute AdvancedSearchForm formResult, BindingResult bindingResult, Model model) {
        AdvancedSearchFormValidator validator = new AdvancedSearchFormValidator();
        validator.validate(formResult, bindingResult);

        if (bindingResult.hasErrors()) {
            return "SearchingForm";
        }

        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
        System.out.println("selectedObjects: " + selectedObjects);

        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        System.out.println("selectedLivingConditions: " + selectedLivingConditions);


        String selectedChildrenCountResult = formResult.getChildren();

        Children selectedChildrenEnum = null;
        if (selectedChildrenCountResult != null) {
            selectedChildrenEnum = Children.getByValueAsString(selectedChildrenCountResult);
            model.addAttribute("selectedChildrenCountResult", selectedChildrenEnum);
            System.out.println("selectedChildrenEnum: " + selectedChildrenEnum);
        }

        model.addAttribute("selectedObjects", selectedObjects);
        model.addAttribute("selectedLivingConditions", selectedLivingConditions);


        String objectAroundVillageDTOS = String.join(",", selectedObjects);
        String livingConditionDTOS = String.join(",", selectedLivingConditions);
        String children = selectedChildrenEnum.name();

        List<VillageDTO> villageDTOs = filterClient.searchVillagesByCriteria(selectedObjects, selectedLivingConditions, children);

        model.addAttribute("villageDTOs", villageDTOs);

        return "result-test";
    }




//    @PostMapping("/search")
//    public String search(@ModelAttribute AdvancedSearchForm formResult, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

//        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
//        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
//        String selectedChildrenCountResult = formResult.getChildren();
//
//        redirectAttributes.addAttribute("objectAroundVillageDTOS", selectedObjects);
//        redirectAttributes.addAttribute("livingConditionDTOS", selectedLivingConditions);
//        redirectAttributes.addAttribute("children", selectedChildrenCountResult);
//
//        return "redirect:/api/v1/filter/searchVillages";
//    }




}
