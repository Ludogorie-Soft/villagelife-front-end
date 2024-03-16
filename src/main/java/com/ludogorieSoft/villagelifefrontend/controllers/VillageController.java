package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.advanced.AddVillageFormValidator;
import com.ludogorieSoft.villagelifefrontend.advanced.InquiryValidator;
import com.ludogorieSoft.villagelifefrontend.advanced.MessageValidator;
import com.ludogorieSoft.villagelifefrontend.advanced.UserValidator;
import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.exceptions.ImageMaxUploadSizeExceededException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import feign.FeignException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private final QuestionClient questionClient;
    private ObjectAroundVillageClient objectAroundVillageClient;
    private PopulatedAssertionClient populatedAssertionClient;
    private LivingConditionClient livingConditionClient;
    private VillageImageClient villageImageClient;
    private final MessageClient messageClient;
    private final InquiryClient inquiryClient;
    private final SubscriptionClient subscriptionClient;
    private final UserValidator userValidator;
    private static final String VILLAGES_ATTRIBUTE = "villages";
    private final MessageValidator messageValidator;
    private final InquiryValidator inquiryValidator;
    private final AddVillageFormValidator addVillageFormValidator;
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String IS_SENT_ATTRIBUTE = "isSent";
    private static final String CONTACTS_VIEW = "contacts";
    private static final String SUBSCRIPTION_ATTRIBUTE = "subscription";
    private static final long MAX_FILE_SIZE = (long) 5 * 1024 * 1024;

    @GetMapping(value = { "/home-page/{page}", "/home-page" })
    public String homePage(Model model, @PathVariable(name = "page", required = false) Integer page) {
        int currentPage = (page != null) ? page : 0;
        List<RegionDTO> regionDTOS = regionClient.getAllRegions();
        model.addAttribute("regions", regionDTOS);
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        model.addAttribute("pagesCount", villageImageClient.getAllApprovedVillageDTOsWithImagesPageCount(currentPage, 6));
        try {
            ResponseEntity<List<VillageDTO>> response = villageImageClient.getAllApprovedVillageDTOsWithImages(currentPage, 6);

            if (response.getStatusCode().is2xxSuccessful()) {
                List<VillageDTO> villageDTOS = response.getBody();
                model.addAttribute(VILLAGES_ATTRIBUTE, villageDTOS);
            } else {
                model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
                model.addAttribute("noVillagesMessage", "error.empty_list");
            }
        } catch (FeignException.NotFound e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
            model.addAttribute("noVillagesMessage", "error.not_approved_villages");
        } catch (FeignException e) {
            e.printStackTrace();
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
            model.addAttribute("errorMessage", "error.take_villages");
        }
        return "HomePage";
    }
    @GetMapping("/show/{id}")
    public String showVillageByVillageId(@PathVariable(name = "id") Long id, Model model) {
        VillageInfo villageInfo = villageClient.getVillageInfoById(id);
        InquiryDTO inquiryDTO = new InquiryDTO();
        getInfoForShowingVillage(villageInfo, inquiryDTO, true, null, model, null, null);
        return "ShowVillageById";
    }

    @PostMapping("/subscription-save")
    public String saveSubscription(@ModelAttribute("subscription") SubscriptionDTO subscriptionDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String subscriptionMessage;
        if (subscriptionClient.emailExists(subscriptionDTO.getEmail())) {
            subscriptionMessage = "subscription.message.exist";
        } else {
            subscriptionClient.createSubscription(subscriptionDTO);
            subscriptionMessage = "subscription.message.thank_you";
        }

        redirectAttributes.addFlashAttribute("subscriptionMessage", subscriptionMessage);
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

    @PostMapping("/inquiry-save")
    public String saveInquiry(@ModelAttribute("inquiry") InquiryDTO inquiryDTO, BindingResult bindingResult, Model model) {
        inquiryValidator.validate(inquiryDTO, bindingResult);
        VillageInfo villageInfo = villageClient.getVillageInfoById(inquiryDTO.getVillageId());

        if (bindingResult.hasErrors()) {
            getInfoForShowingVillage(villageInfo, inquiryDTO, true, null, model, null, null);
            model.addAttribute(IS_SENT_ATTRIBUTE, false);

        } else {
            inquiryClient.createInquiry(inquiryDTO);
            inquiryDTO = new InquiryDTO();

            getInfoForShowingVillage(villageInfo, inquiryDTO, true, null, model, null, null);
            model.addAttribute(IS_SENT_ATTRIBUTE, true);

        }
        return "ShowVillageById";
    }
    protected void getInfoForShowingVillage(VillageInfo villageInfo, InquiryDTO inquiryDTO, boolean status, String answerDate, Model model, AdministratorDTO administratorDTO, String keyWord) {
        model.addAttribute("villageInfo", villageInfo);

        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());

        inquiryDTO.setUserMessage("Здравейте, желая повече информация за [село " + villageInfo.getVillageDTO().getName() + ", област " + villageInfo.getVillageDTO().getRegion() + "]");
        model.addAttribute("inquiry", inquiryDTO);

        List<String> imagesResponse = villageImageClient.getAllImagesForVillage(villageInfo.getVillageDTO().getId(), status, answerDate).getBody();
        model.addAttribute("imageSrcList", imagesResponse);

        List<EthnicityDTO> ethnicityDTOS = ethnicityClient.getAllEthnicities();
        model.addAttribute("ethnicities", ethnicityDTOS);

        List<QuestionDTO> questionDTOS = questionClient.getAllQuestions();
        model.addAttribute("questions", questionDTOS);

        model.addAttribute("answerDate", answerDate);

        model.addAttribute("admin", administratorDTO);

        model.addAttribute("status", keyWord);

    }

    @GetMapping("/create")
    public String showCreateVillageForm(Model model) {
        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();
        return getAddVillagePage(addVillageFormResult, model);
    }
    @PostMapping("/save")
    public String saveVillage(@ModelAttribute("addVillageFormResult") AddVillageFormResult addVillageFormResult, @RequestParam("images") List<MultipartFile> images, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ImageMaxUploadSizeExceededException {
        addVillageFormValidator.validate(addVillageFormResult, bindingResult);
        if (bindingResult.hasErrors()) {
            return getAddVillagePage(addVillageFormResult, model);
        }
        List<byte[]> imageBytes = new ArrayList<>();
        if (images.get(0).getSize() > 0) {
            userValidator.validate(addVillageFormResult.getUserDTO(), bindingResult);
            if(bindingResult.hasErrors()){
                return getAddVillagePage(addVillageFormResult, model);
            }
            hasExceededFileSize(images);
            convertImagesToBytes(images, imageBytes);
        }
        addVillageFormResult.setImageBytes(imageBytes);
        addVillageFormClient.createAddVillageForResult(addVillageFormResult);
        redirectAttributes.addFlashAttribute("saveSuccessful", true);
        return "redirect:/villages/home-page";
    }
    private void hasExceededFileSize(List<MultipartFile> images) throws ImageMaxUploadSizeExceededException {
        long totalSize = images.stream()
                .mapToLong(MultipartFile::getSize)
                .sum();
        if( totalSize > VillageController.MAX_FILE_SIZE){
            throw new ImageMaxUploadSizeExceededException("File size should not exceed 5 MB");
        }
    }

    private String getAddVillagePage(AddVillageFormResult addVillageFormResult, Model model) {
        addAllListsWithOptions(model);
        model.addAttribute("addVillageFormResult", addVillageFormResult);
        return "add-village";
    }

    private List<byte[]> convertImagesToBytes(List<MultipartFile> images, List<byte[]> imageBytes) {
        for (MultipartFile image : images) {
            try {
                byte[] imageData = image.getBytes();
                imageBytes.add(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageBytes;
    }

    @GetMapping("/partners")
    public String showPartnersPage(Model model) {
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        return "partners";
    }

    @GetMapping("/contacts")
    public String showContactsPage(Model model) {
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        model.addAttribute(MESSAGE_ATTRIBUTE, new MessageDTO());
        return CONTACTS_VIEW;
    }

    @PostMapping("/message-save")
    public String saveMessage(@ModelAttribute("message") MessageDTO messageDTO, BindingResult bindingResult, Model model) {
        messageValidator.validate(messageDTO, bindingResult);
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        if (bindingResult.hasErrors()) {
            model.addAttribute(IS_SENT_ATTRIBUTE, false);
            model.addAttribute(MESSAGE_ATTRIBUTE, messageDTO);
        } else {
            model.addAttribute(IS_SENT_ATTRIBUTE, true);
            messageClient.createMessage(messageDTO);
            model.addAttribute(MESSAGE_ATTRIBUTE, new MessageDTO());
        }
        return CONTACTS_VIEW;
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(Model model) {
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
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


    @GetMapping("/general-terms")
    String showGeneralTerms(Model model) {
        model.addAttribute(SUBSCRIPTION_ATTRIBUTE, new SubscriptionDTO());
        return "general-terms";
    }
}
