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
    private ObjectAroundVillageClient objectAroundVillageClient;
    private LivingConditionClient livingConditionClient;
    private final RegionClient regionClient;
    private final VillageImageClient villageImageClient;
    private static final String SEARCHING_FORM_VIEW = "SearchingForm";
    private static final String MESSAGE_ATTRIBUTE = "message";


    @GetMapping("/all")
    public String findAll(
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

        List<VillageDTO> villages;

        villages = fetchVillageDTOsWithImages(region, keyword);

        int resultCount = (villages != null) ? villages.size() : 0;
        model.addAttribute("villages", villages);

        displaySearchResultsMessage(region, keyword, model, resultCount);
        return SEARCHING_FORM_VIEW;
    }


    private List<VillageDTO> fetchVillageDTOsWithImages(String region, String keyword) {
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
                villages = filterClient.getAllApprovedVillages();
            }
        }

        getImagesForVillages(villages);

        return villages;
    }


    private static void displaySearchResultsMessage(String region, String keyword, Model model, int resultCount) {
        if (resultCount > 0) {
            model.addAttribute(MESSAGE_ATTRIBUTE, "Намерени резултати: " + resultCount);
        } else {
            if (region != null && !region.isEmpty()) {
                if (keyword != null && !keyword.isEmpty()) {
                    model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати за избраната област: " + region + " и село: " + keyword);
                } else {
                    model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати за избраната област: " + region);
                }
            } else {
                if (keyword != null && !keyword.isEmpty()) {
                    model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати от вашето търсене за село: " + keyword);
                } else {
                    model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати от търсенето.");
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
            return SEARCHING_FORM_VIEW;
        }

        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        String selectedChildrenCountResult = formResult.getChildren();
        Children selectedChildrenEnum = Children.getByValueAsString(selectedChildrenCountResult);

        List<VillageDTO> villageDTOs = getVillageDTOs(model, selectedObjects, selectedLivingConditions, selectedChildrenEnum);
        model.addAttribute("villages", villageDTOs);

        displayAdvancedSearchResultMessage(model, villageDTOs);

        return SEARCHING_FORM_VIEW;
    }


    private static void displayAdvancedSearchResultMessage(Model model, List<VillageDTO> villageDTOs) {
        int villageCount = villageDTOs.size();
        model.addAttribute("villageCount", villageCount);

        if (villageCount > 0) {
            model.addAttribute(MESSAGE_ATTRIBUTE, "Намерени резултати от разширеното търсене: " + villageCount);
        } else {
            model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати от разширеното търсене!!!");
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

        getImagesForVillages(villageDTOs);

        return villageDTOs;
    }


    private void getImagesForVillages(List<VillageDTO> villages) {
        if (villages != null) {
            for (VillageDTO village : villages) {
                List<String> images = villageImageClient.getAllImagesForVillage(village.getId()).getBody();
                village.setImages(images);
            }
        }
    }


}
