package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private ObjectAroundVillageClient objectAroundVillageClient;
    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;
    private VillageImageClient villageImageClient;
    private final ObjectVillageClient objectVillageClient;
    private final MessageClient messageClient;

    private FilterClient filterClient;


    @GetMapping
    String getVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute("villages", villages);
        return "/test/testAllVillages";
    }
    @GetMapping("/home-page")
    public String homePage(Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);

//         List<VillageDTO> villageList = filterClient.getAllApprovedVillages();
//         model.addAttribute("villages", villageList);

        List<VillageDTO> villageDTOS = villageImageClient.getAllVillageDTOsWithImages().getBody();
        model.addAttribute("villages", villageDTOS);

        return "HomePage";
    }
    @GetMapping("/show/{id}")
    public String getAllTablesByVillageId(@PathVariable(name = "id") Long id, Model model) {
        getTablesVillageById(id,model, null);
        return "ShowVillageById";
    }


    private String getEthnicityNames(List<EthnicityVillageDTO> ethnicityVillages) {

        Set<String> uniqueEthnicities = new HashSet<>();

        for (EthnicityVillageDTO ethnicityVillage : ethnicityVillages) {
            EthnicityDTO ethnicity = ethnicityClient.getEthnicityById(ethnicityVillage.getEthnicityId());
            String ethnicityName = ethnicity.getEthnicityName();

            if (!"няма малцинствени групи".equals(ethnicityName)) {
                uniqueEthnicities.add(ethnicityName);
            }
        }

        StringBuilder ethnicityNames = new StringBuilder();
        for (String ethnicityName : uniqueEthnicities) {
            if (ethnicityNames.length() > 0) {
                ethnicityNames.append(", ");
            }
            ethnicityNames.append(ethnicityName);
        }

        if (ethnicityNames.length() == 0) {
            ethnicityNames.append("няма малцинствени групи");
        }


        return ethnicityNames.toString();
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
    public String showPartnersPage(){
        return "partners";
    }
    @GetMapping("/contacts")
    public String showContactsPage(Model model){
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
    public String showAboutUsPage(){
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
    protected void getTablesVillageById(Long id,Model model, AdministratorDTO administratorDTO){
        List<String> imagesResponse = villageImageClient.getAllImagesForVillage(id).getBody();
        model.addAttribute("imageSrcList", imagesResponse);

        double ecoValue = villageLivingConditionClient.getVillagePopulationAssertionByVillageIdEcoValue(id);
        model.addAttribute("ecoValue", ecoValue);

        double delinquencyValue = villageLivingConditionClient.getVillagePopulationAssertionByVillageIdDelinquencyValue(id);
        model.addAttribute("delinquencyValue", delinquencyValue);

        double livingConditionsValue = villageLivingConditionClient.getVillageLivingConditionsByVillageIdValue(id);
        model.addAttribute("livingConditionsValue", livingConditionsValue);

        List<ObjectAroundVillageDTO> objectAroundVillage = objectAroundVillageClient.getAllObjectsAroundVillage();
        model.addAttribute("objectAroundVillage", objectAroundVillage);

        List<ObjectVillageDTO> objectVillage = objectVillageClient.getObjectVillageByVillageID(id);
        model.addAttribute("objectVillage", objectVillage);

        EthnicityVillageDTO ethnicityVillage = villageEthnicityClient.getEthnicityVillageByVillageId(id);
        model.addAttribute("ethnicityVillage", ethnicityVillage);

        EthnicityDTO ethnicity = ethnicityClient.getEthnicityById(ethnicityVillage.getEthnicityId());
        model.addAttribute("ethnicity", ethnicity);

        PopulationDTO population = populationClient.getPopulationById(id);
        model.addAttribute("population", population);

        VillageDTO village = villageClient.getVillageById(id);
        model.addAttribute("village", village);

        List<QuestionDTO> question = questionClient.getAllQuestions();
        model.addAttribute("question", question);

        List<VillageAnswerQuestionDTO> villageAnswerQuestion = villageAnswerQuestionClient.getVillageAnswerQuestionByVillageId(id);
        model.addAttribute("villageAnswerQuestion", villageAnswerQuestion);

        List<LivingConditionDTO> livingCondition = livingConditionClient.getAllLivingConditions();
        model.addAttribute("livingCondition", livingCondition);

        List<VillageLivingConditionDTO> villageLivingCondition = villageLivingConditionClient.getVillageLivingConditionsByVillageId(id);
        model.addAttribute("villageLivingCondition", villageLivingCondition);

        List<PopulatedAssertionDTO> villagePopulation = populatedAssertionClient.getAllPopulatedAssertion();
        model.addAttribute("villagePopulation", villagePopulation);

        List<VillagePopulationAssertionDTO> villagePopulationAssertion = villagePopulationAssertionClient.getVillagePopulationAssertionByVillageId(id);
        model.addAttribute("villagePopulationAssertion", villagePopulationAssertion);

        model.addAttribute("admin",administratorDTO);
    }

    @GetMapping("/map")
    String mapVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute("villages", villages);
        return "/test/map";
    }

    @GetMapping("/general-terms")
    String showGeneralTerms(Model model) {
        List<VillageDTO> villages = filterClient.getAllApprovedVillages();

        model.addAttribute("villages", villages);
        model.addAttribute("pageTitle", "Общи условия");
        return "/general-terms";
    }

}
