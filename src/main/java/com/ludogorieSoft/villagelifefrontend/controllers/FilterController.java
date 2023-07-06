package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.AdvancedSearchForm;
import com.ludogorieSoft.villagelifefrontend.advanced.AdvancedSearchFormValidator;
import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.enums.Children;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final FilterClient filterClient;

    private final VillageClient villageClient;

    private ObjectAroundVillageClient objectAroundVillageClient;

    private LivingConditionClient livingConditionClient;

    private final RegionClient regionClient;


    @GetMapping("/all")
    public String findAll(
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
        List<VillageDTO> villages;
        int resultCount;


        villages = fetchVillageDTOs(region, keyword);

        resultCount = villages.size();
        model.addAttribute("villages", villages);


        displaySearchResultsMessage(region, keyword, model, resultCount);

        return "SearchingForm";
    }

    private List<VillageDTO> fetchVillageDTOs(String region, String keyword) {
        List<VillageDTO> villages;
        if (region != null && !region.isEmpty()) {
            if (keyword != null && !keyword.isEmpty()) {
                villages = filterClient.getVillageByNameAndRegion(region, keyword);
            } else {
                villages = filterClient.getVillageByRegion(region);
            }
        } else {
            if (keyword != null && !keyword.isEmpty()) {
                villages = filterClient.getVillageByName(keyword);
            } else {
                villages = villageClient.getAllVillages();
            }
        }
        return villages;
    }

    private static void displaySearchResultsMessage(String region, String keyword, Model model, int resultCount) {
        if (resultCount > 0) {
            model.addAttribute("message", "Намерени резултати: " + resultCount);
        } else {
            if (region != null && !region.isEmpty()) {
                if (keyword != null && !keyword.isEmpty()) {
                    model.addAttribute("message", "Не бяха открити резултати за избраната област: " + region + " и село: " + keyword);
                } else {
                    model.addAttribute("message", "Не бяха открити резултати за избраната област: " + region);
                }
            } else {
                if (keyword != null && !keyword.isEmpty()) {
                    model.addAttribute("message", "Не бяха открити резултати от вашето търсене за село: " + keyword);
                } else {
                    model.addAttribute("message", "Не бяха открити резултати от търсенето.");

                }
            }
        }
    }


    @GetMapping("/advancedSearchModalForm")
    public String getPageWithModal(Model model) {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();

        List<ObjectAroundVillageDTO> objectAroundVillageDTOS = objectAroundVillageClient.getAllObjectsAroundVillage();
        model.addAttribute("objectsAroundVillage", objectAroundVillageDTOS);

        List<LivingConditionDTO> livingConditionDTOS = livingConditionClient.getAllLivingConditions();
        model.addAttribute("livingConditions", livingConditionDTOS);

        Children[] childrenValues = Children.values();
        model.addAttribute("childrenValues", childrenValues);

        model.addAttribute("advancedSearchForm", advancedSearchForm);

        return "page-with-modal";
    }


    @PostMapping("/search")
    public String search(@ModelAttribute AdvancedSearchForm formResult, BindingResult bindingResult, Model model) {
        AdvancedSearchFormValidator validator = new AdvancedSearchFormValidator();
        validator.validate(formResult, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Формата е празна");
            return "SearchingForm";
        }


        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();

        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();

        String selectedChildrenCountResult = formResult.getChildren();
        Children selectedChildrenEnum = Children.getByValueAsString(selectedChildrenCountResult);


        List<VillageDTO> villageDTOs = getVillageDTOs(model, selectedObjects, selectedLivingConditions, selectedChildrenEnum);

        model.addAttribute("villages", villageDTOs);

        displayAdvancedSearchResultMessage(model, villageDTOs);

//        return "result-test";

        return "SearchingForm";

    }

    private static void displayAdvancedSearchResultMessage(Model model, List<VillageDTO> villageDTOs) {
        int villageCount = villageDTOs.size();
        model.addAttribute("villageCount", villageCount);

        if (villageCount > 0) {
            model.addAttribute("message", "Намерени резултати от разширеното търсене: " + villageCount);
        } else {
            model.addAttribute("message", "Не бяха открити резултати от разширеното търсене!!!");
        }
    }


    private List<VillageDTO> getVillageDTOs(Model model, List<String> selectedObjects, List<String> selectedLivingConditions, Children selectedChildrenEnum) {
        List<VillageDTO> villageDTOs;
        model.addAttribute("selectedObjects", selectedObjects);
        model.addAttribute("selectedChildrenCountResult", selectedChildrenEnum);
        model.addAttribute("selectedLivingConditions", selectedLivingConditions);


        if (selectedObjects != null && selectedChildrenEnum != null && selectedLivingConditions != null) {
            villageDTOs = filterClient.searchVillagesByCriteria(selectedObjects, selectedLivingConditions, selectedChildrenEnum.name());
        } else {
            if (selectedObjects == null) {
                if (selectedChildrenEnum == null) {
                    villageDTOs = filterClient.searchVillagesByLivingCondition(selectedLivingConditions);
                } else {
                    if (selectedLivingConditions == null) {
                        villageDTOs = filterClient.searchVillagesByChildrenCount(selectedChildrenEnum.name());
                    } else {
                        villageDTOs = filterClient.searchVillagesByLivingConditionAndChildren(selectedLivingConditions, selectedChildrenEnum.name());
                    }
                }
            } else if (selectedChildrenEnum == null) {
                if (selectedLivingConditions == null) {
                    villageDTOs = filterClient.searchVillagesByObject(selectedObjects);
                } else {
                    villageDTOs = filterClient.searchVillagesByObjectAndLivingCondition(selectedObjects, selectedLivingConditions);
                }
            } else {
                villageDTOs = filterClient.searchVillagesByObjectAndChildren(selectedObjects, selectedChildrenEnum.name());
            }
        }
        return villageDTOs;

    }


}
