package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    private ObjectAroundVillageClient objectAroundVillageClient;
    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;
    private VillageImageClient villageImageClient;
    private final ObjectVillageClient objectVillageClient;
    private final MessageClient messageClient;


    @GetMapping
    String getVillages(Model model) {
        List<VillageDTO> villages = villageClient.getAllVillages();
        model.addAttribute("villages", villages);
        return "/test/testAllVillages";
    }
    @GetMapping("/test/{imageName}")
    public String showImage(@PathVariable String imageName, Model model) {
        ResponseEntity<byte[]> imageResponse = villageImageClient.getImage(imageName);

        if (imageResponse.getStatusCode().is2xxSuccessful()) {
            byte[] imageBytes = imageResponse.getBody();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String imageSrc = "data:image/jpeg;base64," + base64Image;
            model.addAttribute("imageSrc", imageSrc);
        }
        return "/test/test";
    }
    @GetMapping("/home-page")
    public String homePage(Model model) {
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
        List<VillageDTO> villageList = villageClient.getAllVillages();
        model.addAttribute("villages", villageList);
        List<VillageImageResponse> villageImageResponses = villageImageClient.getAllVillageImageResponses().getBody();
        model.addAttribute("villageImageResponses", villageImageResponses);
        return "HomePage";
    }
    @GetMapping("/show/{id}")
    public String getAllTablesByVillageId(@PathVariable(name = "id") Long id, Model model) {
        ResponseEntity<List<String>> imagesResponse = villageImageClient.getAllImagesForVillage(id);
        if (imagesResponse.getStatusCode().is2xxSuccessful()) {
            List<String> base64Images = imagesResponse.getBody();
            List<String> imageSrcList = new ArrayList<>();
            for (String base64Image : base64Images) {
                String imageSrc = "data:image/jpeg;base64," + base64Image;
                imageSrcList.add(imageSrc);
            }
            model.addAttribute("imageSrcList", imageSrcList);
        }

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

        return "ShowVillageById";
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
}
