package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.AdvancedSearchForm;
import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.enums.Children;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/advancedSearchModalForm")
    public String getPageWithModal(Model model) {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();

        List<ObjectAroundVillageDTO> objectAroundVillageDTOS = objectAroundVillageClient.getAllObjectsAroundVillage();
        objectAroundVillageDTOS.removeIf(dto -> "objects.fourteenth".equals(dto.getType()));
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
                         @RequestParam(name = "region", required = false) String region,
                         @RequestParam(name = "keyword", required = false) String villageName,
                         @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
                         BindingResult bindingResult, Model model) {
//        AdvancedSearchFormValidator validator = new AdvancedSearchFormValidator();
//        validator.validate(formResult, bindingResult);

        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Формата е празна");
            model.addAttribute("subscription", new SubscriptionDTO());
            return SEARCHING_FORM_VIEW;
        }

        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        String selectedChildrenEnum;
        if (formResult.getChildren() != null) {
            String selectedChildrenCountResult = formResult.getChildren();
            selectedChildrenEnum = Children.getByValueAsString(selectedChildrenCountResult).toString();
        } else {
            selectedChildrenEnum = null;
        }

        Page<VillageDTO> villageDTOs = getVillageDTOs(model,region, villageName, selectedObjects, selectedLivingConditions, selectedChildrenEnum, sort, page);
        model.addAttribute("sort", sort);
        model.addAttribute("villages", villageDTOs);
        model.addAttribute("subscription", new SubscriptionDTO());
        displayAdvancedSearchResultMessage(model, villageDTOs.getTotalElements(),villageDTOs.getTotalPages());
        return SEARCHING_FORM_VIEW;
    }

    private static void displayAdvancedSearchResultMessage(Model model, long resultCount, long pageSize) {
        model.addAttribute("villageCount", resultCount);

        if (resultCount > 0) {
            model.addAttribute(MESSAGE_ATTRIBUTE, "filter.controller.search_results_found");
            model.addAttribute("pageSize", pageSize);
        } else {
            model.addAttribute(MESSAGE_ATTRIBUTE, "filter.controller.search_results_notFound");
        }
    }

    private Page<VillageDTO> getVillageDTOs(Model model, String region, String villageName, List<String> selectedObjects, List<String> selectedLivingConditions,
                                            String selectedChildrenEnum, String sort, int page) {
        Page<VillageDTO> villageDTOs;
        model.addAttribute("selectedObjects", selectedObjects);
        model.addAttribute("selectedChildrenCountResult", selectedChildrenEnum);
        model.addAttribute("selectedLivingConditions", selectedLivingConditions);

        String[] sortParams = sort.split(",");
        String sortBy = sortParams[0];
        String sortDir = sortParams.length > 1 ? sortParams[1] : "asc";
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.fromString(sortDir.toUpperCase()), sortBy));


        villageDTOs = filterClient.searchVillagesByCriteria(region, villageName, selectedObjects, selectedLivingConditions, selectedChildrenEnum, pageable);
        boolean status = true;
        String date = null;
        getImagesForVillages(villageDTOs.toList(), status, date);
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
            int lastSlashIndex = referer.lastIndexOf('/');
            int lastQuestionMarkIndex = referer.lastIndexOf('?');
            String baseRedirectUrl = referer.substring(0, lastSlashIndex + 1);
            String queryString = lastQuestionMarkIndex != -1 ? referer.substring(lastQuestionMarkIndex) : "";

            String newRedirectUrl = baseRedirectUrl + page + queryString;
            return "redirect:" + newRedirectUrl;
        }
        return "redirect:" + referer;
    }
}
