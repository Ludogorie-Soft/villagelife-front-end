package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.InquiryValidator;
import com.ludogorieSoft.villagelifefrontend.advanced.MessageValidator;
import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import feign.FeignException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/villages")
public class VillageController {
    private final RegionClient regionClient;
    private final VillageClient villageClient;
    private final AddVillageFormClient addVillageFormClient;
    private final GroundCategoryClient groundCategoryClient;
    private final EthnicityClient ethnicityClient;
    private final PopulationClient populationClient;
    private final QuestionClient questionClient;
    private ObjectAroundVillageClient objectAroundVillageClient;
    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;
    private VillageImageClient villageImageClient;
    private final MessageClient messageClient;
    private  InquiryClient inquiryClient;
    private static final String VILLAGES_ATTRIBUTE = "villages";
    private final MessageValidator messageValidator;
    private final InquiryValidator inquiryValidator;


    @GetMapping
    String getVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute(VILLAGES_ATTRIBUTE, villages);
        return "/test/testAllVillages";
    }


    @GetMapping("/home-page")
    public String homePage(Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

        try {
            ResponseEntity<List<VillageDTO>> response = villageImageClient.getAllApprovedVillageDTOsWithImages();

            if (response.getStatusCode().is2xxSuccessful()) {
                List<VillageDTO> villageDTOS = response.getBody();
                model.addAttribute(VILLAGES_ATTRIBUTE, villageDTOS);
            } else {
                model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
                model.addAttribute("noVillagesMessage", "Списъкът с одобрени села е празен!");
            }
        } catch (FeignException.NotFound e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
            model.addAttribute("noVillagesMessage", "В момента няма одобрени от администратор села!!!");
        } catch (FeignException e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
            model.addAttribute("errorMessage", "Грешка при получаване на одобрените села!");
        }

        return "HomePage";
    }


    @GetMapping("/show/{id}")
    public String showVillageByVillageId(@PathVariable(name = "id") Long id, Model model) {
        VillageInfo villageInfo = villageClient.getVillageInfoById(id);
        InquiryDTO inquiryDTO = new InquiryDTO();
        getInfoForShowingVillage(villageInfo, inquiryDTO, model);
        return "ShowVillageById";
    }
    @PostMapping("/inquiry-save")
    public String saveInquiry(@ModelAttribute("inquiry") InquiryDTO inquiryDTO, BindingResult bindingResult, Model model) {
        inquiryValidator.validate(inquiryDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            VillageInfo villageInfo = villageClient.getVillageInfoById(inquiryDTO.getVillageId());
            getInfoForShowingVillage(villageInfo, inquiryDTO, model);
            model.addAttribute("isSent", false);
        }else {
            inquiryClient.createInquiry(inquiryDTO);
            VillageInfo villageInfo = villageClient.getVillageInfoById(inquiryDTO.getVillageId());
            inquiryDTO = new InquiryDTO();
            getInfoForShowingVillage(villageInfo, inquiryDTO, model);
            model.addAttribute("isSent", true);
        }
        return "ShowVillageById";
    }

    private void getInfoForShowingVillage(VillageInfo villageInfo, InquiryDTO inquiryDTO, Model model){
        model.addAttribute("villageInfo", villageInfo);

        inquiryDTO.setUserMessage("Здравейте, желая повече информация за [село " + villageInfo.getVillageDTO().getName() + ", област " + villageInfo.getVillageDTO().getRegion() + "]");
        model.addAttribute("inquiry", inquiryDTO);

        List<String> imagesResponse = villageImageClient.getAllImagesForVillage(villageInfo.getVillageDTO().getId()).getBody();
        model.addAttribute("imageSrcList", imagesResponse);

        PopulationDTO population = populationClient.getPopulationById(villageInfo.getVillageDTO().getId());
        model.addAttribute("population", population);

        List<EthnicityDTO> ethnicityDTOS = ethnicityClient.getAllEthnicities();
        model.addAttribute("ethnicities", ethnicityDTOS);

        List<QuestionDTO> questionDTOS = questionClient.getAllQuestions();
        model.addAttribute("questions", questionDTOS);

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
        return "redirect:/villages/home-page";
    }

    @GetMapping("/partners")
    public String showPartnersPage() {
        return "partners";
    }

    @GetMapping("/contacts")
    public String showContactsPage(Model model) {
        MessageDTO messageDTO = new MessageDTO();
        model.addAttribute("message", messageDTO);
        return "contacts";
    }

    @PostMapping("/message-save")
    public String saveMessage(@ModelAttribute("message") MessageDTO messageDTO, BindingResult bindingResult, Model model) {
        messageValidator.validate(messageDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isSent", false);
            model.addAttribute("message", messageDTO);
            return "contacts";
        }else {
            model.addAttribute("isSent", true);
            messageClient.createMessage(messageDTO);
            model.addAttribute("message", new MessageDTO());
        }
        return "contacts";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage() {
        return "about-us";
    }

    private void addAllListsWithOptions(Model model) {
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

    @GetMapping("/map")
    String mapVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute(VILLAGES_ATTRIBUTE, villages);
        return "/test/map";
    }

    @GetMapping("/general-terms")
    String showGeneralTerms(Model model) {
        try {
            ResponseEntity<List<VillageDTO>> response = villageImageClient.getAllApprovedVillageDTOsWithImages();

            if (response.getStatusCode().is2xxSuccessful()) {
                List<VillageDTO> villageDTOS = response.getBody();
                model.addAttribute(VILLAGES_ATTRIBUTE, villageDTOS);
            }
        } catch (FeignException.NotFound e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
        }

        model.addAttribute("pageTitle", "Общи условия");
        return "/general-terms";
    }

}
