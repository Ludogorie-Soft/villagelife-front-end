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

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
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
    private static Long resultCount = 0L;

    @GetMapping("/all/{page}")
    public String findAll(
            @PathVariable("page") int page,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sort", required = false, defaultValue = "default") String sort,
            Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

        List<VillageDTO> villages;

        villages = fetchVillageDTOsWithImages(region, keyword, sort, page);

        sortVillages(sort, villages);

        model.addAttribute("villageCount", resultCount);
        model.addAttribute("villages", villages);
        model.addAttribute("sort", sort);
        model.addAttribute("subscription", new SubscriptionDTO());
        displaySearchResultsMessage(region, keyword, model, resultCount);
        return SEARCHING_FORM_VIEW;
    }


    private void sortVillages(String sort, List<VillageDTO> villages) {
        if ("nameAsc".equals(sort)) {
            villages.sort(Comparator.comparing(VillageDTO::getName));
        } else if ("nameDesc".equals(sort)) {
            villages.sort(Comparator.comparing(VillageDTO::getName).reversed());
        } else if ("regionAsc".equals(sort)) {
            villages.sort(Comparator.comparing(VillageDTO::getRegion).thenComparing(VillageDTO::getName));
        } else if ("regionDesc".equals(sort)) {
            villages.sort(Comparator.comparing(VillageDTO::getRegion).reversed().thenComparing(VillageDTO::getName));
        }
    }


    private List<VillageDTO> fetchVillageDTOsWithImages(String region, String keyword, String sort, int page) {
        List<VillageDTO> villages;
        if (region != null && !region.isEmpty()) {
            if (keyword != null && !keyword.isEmpty()) {
                villages = filterClient.getVillageByNameAndRegion(page, region, keyword, sort);
                resultCount = filterClient.getVillageByNameAndRegionElementsCount(page, region, keyword, sort);
            } else {
                villages = filterClient.getVillageByRegion(page, region);
                resultCount = filterClient.getVillageByRegionElementsCount(page, region);
            }
        } else {
            if (keyword != null && !keyword.isEmpty()) {
                villages = filterClient.getVillageByName(page, keyword);
                resultCount = filterClient.getVillageByNameElementsCount(page, keyword);
            } else {
                villages = filterClient.getAllApprovedVillages(page);
                resultCount = filterClient.getAllApprovedVillagesElementsCount(page);
            }
        }
        boolean status = true;
        String date = null;
        getImagesForVillages(villages,status,date);

        return villages;
    }


    private static void displaySearchResultsMessage(String region, String keyword, Model model, Long resultCount) {
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
        objectAroundVillageDTOS.removeIf(dto -> "областен град".equals(dto.getType()));
        model.addAttribute("objectsAroundVillage", objectAroundVillageDTOS);

        List<LivingConditionDTO> livingConditionDTOS = livingConditionClient.getAllLivingConditions();
        model.addAttribute("livingConditions", livingConditionDTOS);

        Children[] childrenValues = Children.values();
        model.addAttribute("childrenValues", childrenValues);

        model.addAttribute("advancedSearchForm", advancedSearchForm);

        return "page-with-modal";
    }


    @GetMapping("/search/{page}")
    public String search(@ModelAttribute AdvancedSearchForm formResult,
                         @PathVariable("page") int page,
                         @RequestParam(name = "sort", required = false, defaultValue = "default") String sort,
                         BindingResult bindingResult, Model model) {

        AdvancedSearchFormValidator validator = new AdvancedSearchFormValidator();
        validator.validate(formResult, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Формата е празна");
            model.addAttribute("subscription", new SubscriptionDTO());
            return SEARCHING_FORM_VIEW;
        }

        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        String selectedChildrenCountResult = formResult.getChildren();
        Children selectedChildrenEnum = Children.getByValueAsString(selectedChildrenCountResult);

        List<VillageDTO> villageDTOs = getVillageDTOs(model, selectedObjects, selectedLivingConditions, selectedChildrenEnum, sort, page);
        sortVillages(sort, villageDTOs);

        model.addAttribute("villages", villageDTOs);
        model.addAttribute("subscription", new SubscriptionDTO());
        displayAdvancedSearchResultMessage(model);

        return SEARCHING_FORM_VIEW;
    }

    private static void displayAdvancedSearchResultMessage(Model model) {
        model.addAttribute("villageCount", resultCount);

        if (resultCount > 0) {
            model.addAttribute(MESSAGE_ATTRIBUTE, "Намерени резултати от разширеното търсене: " + resultCount);
        } else {
            model.addAttribute(MESSAGE_ATTRIBUTE, "Не бяха открити резултати от разширеното търсене!!!");
        }
    }


    private List<VillageDTO> getVillageDTOs(Model model, List<String> selectedObjects, List<String> selectedLivingConditions,
                                            Children selectedChildrenEnum, String sort, int page) {
        List<VillageDTO> villageDTOs;
        model.addAttribute("selectedObjects", selectedObjects);
        model.addAttribute("selectedChildrenCountResult", selectedChildrenEnum);
        model.addAttribute("selectedLivingConditions", selectedLivingConditions);

        if (selectedObjects != null && selectedChildrenEnum != null && selectedLivingConditions != null) {
            villageDTOs = filterClient.searchVillagesByCriteria(page, selectedObjects, selectedLivingConditions, selectedChildrenEnum.name(), sort);
            resultCount = filterClient.searchVillagesByCriteriaElementsCount(page, selectedObjects, selectedLivingConditions, selectedChildrenEnum.name(), sort);
        } else {
            if (selectedObjects == null) {
                if (selectedChildrenEnum == null) {
                    villageDTOs = filterClient.searchVillagesByLivingCondition(page, selectedLivingConditions);
                    resultCount = filterClient.searchVillagesByLivingConditionElementsCount(page, selectedLivingConditions);
                } else {
                    if (selectedLivingConditions == null) {
                        villageDTOs = filterClient.searchVillagesByChildrenCount(page, selectedChildrenEnum.name());
                        resultCount = filterClient.searchVillagesByChildrenCountElementsCount(page, selectedChildrenEnum.name());
                    } else {
                        villageDTOs = filterClient.searchVillagesByLivingConditionAndChildren(page, selectedLivingConditions, selectedChildrenEnum.name());
                        resultCount = filterClient.searchVillagesByLivingConditionAndChildrenElementsCount(page, selectedLivingConditions, selectedChildrenEnum.name());
                    }
                }
            } else if (selectedChildrenEnum == null) {
                if (selectedLivingConditions == null) {
                    villageDTOs = filterClient.searchVillagesByObject(page, selectedObjects);
                    resultCount = filterClient.searchVillagesByObjectElementsCount(page, selectedObjects);
                } else {
                    villageDTOs = filterClient.searchVillagesByObjectAndLivingCondition(page, selectedObjects, selectedLivingConditions);
                    resultCount = filterClient.searchVillagesByObjectAndLivingConditionElementsCount(page, selectedObjects, selectedLivingConditions);
                }
            } else {
                villageDTOs = filterClient.searchVillagesByObjectAndChildren(page, selectedObjects, selectedChildrenEnum.name());
                resultCount = filterClient.searchVillagesByObjectAndChildrenElementsCount(page, selectedObjects, selectedChildrenEnum.name());
            }
        }
        boolean status = true;
        String date = null;
        getImagesForVillages(villageDTOs,status,date);
        return villageDTOs;
    }


    private void getImagesForVillages(List<VillageDTO> villages, boolean status, String date) {
        if (villages != null) {
            for (VillageDTO village : villages) {
                List<String> images = villageImageClient.getAllImagesForVillage(village.getId(), status, date).getBody();
                village.setImages(images);
            }
        }
    }


    @GetMapping("/change/{page}/{totalElements}")
    public String changePage(@PathVariable("page") int page, @PathVariable("totalElements") int totalElements, HttpServletRequest request) {
        int totalPages = (totalElements + 6 - 1) / 6;
        String referer = request.getHeader("referer");
        if (page < totalPages && page >= 0) {
            System.out.println("1111111111");
            int lastSlashIndex = referer.lastIndexOf('/');
            int lastQuestionMarkIndex = referer.lastIndexOf('?');
            String baseRedirectUrl = referer.substring(0, lastSlashIndex + 1);
            String queryString = lastQuestionMarkIndex != -1 ? referer.substring(lastQuestionMarkIndex) : "";

            String newRedirectUrl = baseRedirectUrl + page + queryString;
            return "redirect:" + newRedirectUrl;
        }
        System.out.println("222222222");
        return "redirect:" + referer;
    }
}
