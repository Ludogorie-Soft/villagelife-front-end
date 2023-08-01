package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import feign.FeignException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//        VillageInfo villageInfo = villageClient.getVillageInfoById(id);
//        model.addAttribute("villageInfo", villageInfo);
//
//        InquiryDTO inquiryDTO = new InquiryDTO();
//        inquiryDTO.setUserMessage("Здравейте, желая повече информация за [село " + villageInfo.getVillageDTO().getName() + ", област " + villageInfo.getVillageDTO().getRegion() + "]");
//        model.addAttribute("inquiry", inquiryDTO);
//
//        List<String> imagesResponse = villageImageClient.getAllImagesForVillage(id).getBody();
//        model.addAttribute("imageSrcList", imagesResponse);
//
//        PopulationDTO population = populationClient.getPopulationById(id);
//        model.addAttribute("population", population);
//
//        List<EthnicityDTO> ethnicityDTOS = ethnicityClient.getAllEthnicities();
//        model.addAttribute("ethnicities", ethnicityDTOS);
//
//        List<QuestionDTO> questionDTOS = questionClient.getAllQuestions();
//        model.addAttribute("questions", questionDTOS);

        AdministratorDTO administratorDTO = null;
        getTablesVillageById(id, model, administratorDTO);

        return "ShowVillageById";
    }
    @PostMapping("/inquiry-save")
    public String saveInquiry(@ModelAttribute("inquiry") InquiryDTO inquiryDTO) {
        inquiryClient.createInquiry(inquiryDTO);
        return "redirect:/villages/show/" + inquiryDTO.getVillageId();
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
    public String saveMessage(@ModelAttribute("message") MessageDTO messageDTO) {
        messageClient.createMessage(messageDTO);
        return "redirect:/villages/contacts";
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

    protected void getTablesVillageById(Long id, Model model, AdministratorDTO administratorDTO) {
        VillageInfo villageInfo = villageClient.getVillageInfoById(id);
        model.addAttribute("villageInfo", villageInfo);

        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setUserMessage("Здравейте, желая повече информация за [село " + villageInfo.getVillageDTO().getName() + ", област " + villageInfo.getVillageDTO().getRegion() + "]");
        model.addAttribute("inquiry", inquiryDTO);

        List<String> imagesResponse = villageImageClient.getAllImagesForVillage(id).getBody();
        model.addAttribute("imageSrcList", imagesResponse);

        PopulationDTO population = populationClient.getPopulationById(id);
        model.addAttribute("population", population);

        List<EthnicityDTO> ethnicityDTOS = ethnicityClient.getAllEthnicities();
        model.addAttribute("ethnicities", ethnicityDTOS);

        List<QuestionDTO> questionDTOS = questionClient.getAllQuestions();
        model.addAttribute("questions", questionDTOS);

        model.addAttribute("admin", administratorDTO);
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

    //    @GetMapping("/update/{villageId}")
//    public ResponseEntity<VillageDTO> findVillageById(@PathVariable(name = "villageId") Long id) {
//        ResponseEntity<VillageDTO> village = villageClient.findVillageById(id);
////        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();
//
//        return village;
//    }
//    @GetMapping(value = "/update")
//    public String findVillageById(Model model) {
//        ResponseEntity<VillageResponse> villages = villageClient.findUnapprovedVillageResponseByVillageId();
//        System.out.println("controller " + villages);
//        model.addAttribute("nestedVillageResponses",villages.getBody().getNestedVillageResponses());
//        model.addAttribute("groupedAnswers", villages.getBody().getGroupedAnswers());
//
//        return "testForm";
//    }
    @GetMapping("/update")
    public String findUnapprovedVillageResponseByVillageId(Model model) {
        ResponseEntity<List<VillageResponse>> villageResponses = villageClient.findUnapprovedVillageResponseByVillageId();
        System.out.println(villageResponses);

        List<VillageResponse> villages = villageResponses.getBody();
        model.addAttribute("villages", villages);
        return "testForm";
    }
    @PostMapping("/approve-answer/{villageId}")
    public ResponseEntity<String> approveAnswer(@RequestParam("villageId") Long villageId,
                                                @RequestParam("answerDate") String answerDate) {
        System.out.println( "from form " + villageId + " " + answerDate);
        String dateTimeString = "2023-07-29T21:44:12";
        String formattedDateTime = dateTimeString.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDateTime, formatter);
        System.out.println("converted "  + villageId + " " + localDateTime);

        String message = "Answer for Village ID " + villageId + " and Answer Date " + answerDate + " is approved.";

        return ResponseEntity.ok(message);
    }
}
