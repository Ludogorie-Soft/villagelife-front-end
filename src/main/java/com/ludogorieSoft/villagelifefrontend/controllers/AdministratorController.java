package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import com.ludogorieSoft.villagelifefrontend.enums.Role;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;


@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdministratorController {
    private final AdminFunctionClient adminFunctionClient;
    private final AdminClient adminClient;
    private final VillageController villageController;

    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String MESSAGE = "message";
    private static final String VILLAGES_ATTRIBUTE = "villages";
    private final VillageClient villageClient;
    private final VillageImageClient villageImageClient;
    private final SubscriptionClient subscriptionClient;
    private final MessageClient messageClient;
    private final InquiryClient inquiryClient;
    private final VillageAnswerQuestionClient villageAnswerQuestionClient;
    private final VillageVideoClient villageVideoClient;

    @GetMapping
    public String getAllAdmins(Model model, HttpSession session) {
        String token = (String) session.getAttribute(SESSION_NAME);
        ResponseEntity<List<AdministratorDTO>> administrators = adminClient.getAllAdministrators(AUTH_HEADER + token);
        List<AdministratorDTO> allAdmins = administrators.getBody();
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        model.addAttribute("adminsAll", allAdmins);
        return "admin_templates/admin_all";
    }

    @GetMapping("/edit/{adminId}")
    public String editAdmin(@PathVariable(name = "adminId") Long adminId, Model model, HttpSession session) {
        String token = (String) session.getAttribute(SESSION_NAME);

        AdministratorDTO admin = (AdministratorDTO) session.getAttribute("info");
        if (admin != null) {
            model.addAttribute(ADMINS, admin.getFullName());
        }

        ResponseEntity<AdministratorDTO> adminById = adminClient.getAdministratorById(adminId, AUTH_HEADER + token);
        AdministratorDTO administratorDTO = adminById.getBody();
        model.addAttribute("adminById", administratorDTO);
        model.addAttribute("roles", Role.ADMIN);
        return "admin_templates/update_admin";
    }

    @PostMapping("/update/{adminId}")
    public String updateAdmin(@PathVariable("adminId") Long adminId,
                              @Valid @ModelAttribute("adminById") AdministratorRequest administratorRequest,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              HttpSession session, Model model
    ) {
        if (bindingResult.hasErrors()) {
            AdministratorDTO admin = (AdministratorDTO) session.getAttribute("info");
            model.addAttribute(ADMINS, admin.getFullName());
            model.addAttribute("roles", Role.ADMIN);
            return "admin_templates/update_admin";
        }
        administratorRequest.setCreatedAt(now());

        String token = (String) session.getAttribute(SESSION_NAME);
        adminClient.updateAdministrator(adminId, administratorRequest, AUTH_HEADER + token);

        redirectAttributes.addFlashAttribute(MESSAGE, "Administrator with ID: " + adminId + " successfully updated !!!");
        return "redirect:/admins";
    }

    @PostMapping("/delete/{adminId}")
    public ModelAndView deleteAdmin(@PathVariable(name = "adminId") Long adminId,
                                    RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminClient.deleteAdministratorById(adminId, AUTH_HEADER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Administrator with ID: " + adminId + " successfully deleted !!!");
        return new ModelAndView("redirect:/admins");

    }

    @GetMapping("/logout")
    public ModelAndView logoutButton(HttpSession session) {
        session.removeAttribute(SESSION_NAME);
        return new ModelAndView("redirect:/auth/login");
    }

    @PostMapping("/village-delete/{villageId}")
    public ModelAndView deleteVillage(@PathVariable(name = "villageId") Long villageId,
                                      RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminFunctionClient.deleteVillageById(villageId, AUTH_HEADER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Village with ID: " + villageId + " successfully deleted !!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/show/{villageId}")
    public String seeVillageToApproveIt(@RequestParam("villageId") Long villageId,
                                        @RequestParam("answerDate") String answerDate, @RequestParam("archived") String archived, Model model, HttpSession session) {
        model.addAttribute("subscription", new SubscriptionDTO());
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        boolean status = false;
        String token2 = (String) session.getAttribute(SESSION_NAME);
        VillageInfo villageInfo = adminFunctionClient.getVillageInfoById(villageId, answerDate, status, AUTH_HEADER + token2);
        InquiryDTO inquiryDTO = new InquiryDTO();
        villageController.getInfoForShowingVillage(villageInfo, inquiryDTO, status, answerDate, model, administratorDTO, archived);
        return "ShowVillageById";
    }

    @GetMapping("/manage-images/{villageId}")
    public String manageImages(@PathVariable("villageId") Long villageId, Model model, HttpSession session) {
        VillageDTO villageDTO = villageClient.getVillageById(villageId);
        model.addAttribute("village", villageDTO);
        String token2 = (String) session.getAttribute(SESSION_NAME);
        List<VillageImageDTO> villageImageDTOs = villageImageClient.getNotDeletedVillageImageDTOsByVillageId(villageId, AUTH_HEADER + token2);
        model.addAttribute("villageImageDTOs", villageImageDTOs);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/admin_images";
    }

    @GetMapping("/image-reject/{villageImageId}")
    public String rejectImage(@PathVariable("villageImageId") Long villageImageId, HttpSession session) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        String token2 = (String) session.getAttribute(SESSION_NAME);
        villageImageClient.rejectVillageImage(villageImageId, AUTH_HEADER + token2);
        return "redirect:/admins/manage-images/" + villageImageDTO.getVillageId() + "?villageId=" + villageImageDTO.getVillageId();
    }

    @PostMapping("/save-images")
    public String saveImages(@RequestParam("newImages") List<MultipartFile> images,
                             @RequestParam("villageId") Long villageId, HttpSession session) {
        List<byte[]> imageBytes = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                byte[] imageData = image.getBytes();
                imageBytes.add(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String token2 = (String) session.getAttribute(SESSION_NAME);
        villageImageClient.adminUploadImages(villageId, imageBytes, AUTH_HEADER + token2);
        return "redirect:/admins/manage-images/" + villageId + "?villageId=" + villageId;
    }

    @PostMapping("/approve/{villageId}")
    public ModelAndView approveVillageResponse(@RequestParam("villageId") Long villageId,
                                               @RequestParam("answerDate") String answerDate, RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminFunctionClient.changeVillageStatus(villageId, answerDate, AUTH_HEADER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Response of village with ID: " + villageId + " approved successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/village")
    public String getUnapprovedVillageResponses(Model model, HttpSession session) {
        model.addAttribute("subscription", new SubscriptionDTO());
        String token2 = (String) session.getAttribute(SESSION_NAME);
        try {
            ResponseEntity<List<VillageResponse>> villageResponses = adminFunctionClient.getUnapprovedVillageResponses(AUTH_HEADER + token2);

            if (villageResponses.getStatusCode().is2xxSuccessful()) {
                AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
                model.addAttribute(ADMINS, administratorDTO.getFullName());
                List<VillageResponse> villages = villageResponses.getBody();
                model.addAttribute("status", "toApproved");
                model.addAttribute(VILLAGES_ATTRIBUTE, villages);
            } else {
                model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
            }
        } catch (FeignException.BadRequest e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
        }

        return "admin_templates/admin_menu";
    }

    @PostMapping("/reject/{villageId}")
    public ModelAndView rejectVillageResponse(@RequestParam("villageId") Long villageId,
                                              @RequestParam("answerDate") String answerDate,
                                              RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminFunctionClient.rejectVillageResponse(villageId, answerDate, AUTH_HEADER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Response of village with ID: " + villageId + " rejected successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/deleted-images/{villageId}")
    public String getDeletedImages(@PathVariable("villageId") Long villageId, Model model, HttpSession session) {
        VillageDTO villageDTO = villageClient.getVillageById(villageId);
        model.addAttribute("village", villageDTO);
        String token2 = (String) session.getAttribute(SESSION_NAME);
        List<VillageImageDTO> villageImageDTOs = villageImageClient.getDeletedVillageImageDTOsByVillageId(villageId, AUTH_HEADER + token2);
        model.addAttribute("villageImageDTOs", villageImageDTOs);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/deleted_images";
    }

    @GetMapping("/delete/{villageImageId}")
    public String deleteImage(@PathVariable("villageImageId") Long villageImageId, HttpSession session) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        Long villageId = villageImageDTO.getVillageId();
        String token2 = (String) session.getAttribute(SESSION_NAME);
        villageImageClient.deleteImageFileById(villageImageId, AUTH_HEADER + token2);
        return "redirect:/admins/deleted-images/" + villageId;
    }

    @GetMapping("/getRejected")
    public String getVillagesWithRejectedResponses(Model model, HttpSession session) {
        String token = (String) session.getAttribute(SESSION_NAME);

        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");

        model.addAttribute(ADMINS, administratorDTO.getFullName());
        try {
            ResponseEntity<List<VillageResponse>> villageResponses = adminFunctionClient.getVillagesWithRejectedResponses(AUTH_HEADER + token);

            if (villageResponses.getStatusCode().is2xxSuccessful()) {

                List<VillageResponse> villages = villageResponses.getBody();

                model.addAttribute("status", "archived");
                model.addAttribute(VILLAGES_ATTRIBUTE, villages);

            }
        } catch (FeignException.BadRequest e) {
            model.addAttribute(VILLAGES_ATTRIBUTE, Collections.emptyList());
        }
        return "admin_templates/admin_menu";
    }

    @GetMapping("/resume/{villageImageId}")
    public String resumeImage(@PathVariable("villageImageId") Long villageImageId, HttpSession session) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        Long villageId = villageImageDTO.getVillageId();
        String token2 = (String) session.getAttribute(SESSION_NAME);
        villageImageClient.resumeImageVillageById(villageImageId, AUTH_HEADER + token2);
        return "redirect:/admins/deleted-images/" + villageId;
    }

    @GetMapping("/approve-image/{villageImageId}")
    public String approveImage(@PathVariable("villageImageId") Long villageImageId, HttpSession session) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        villageImageDTO.setStatus(true);
        String token2 = (String) session.getAttribute(SESSION_NAME);
        villageImageClient.updateVillageImage(villageImageId, villageImageDTO, AUTH_HEADER + token2);
        return "redirect:/admins/manage-images/" + villageImageDTO.getVillageId();
    }

    @GetMapping("/subscriptions")
    public String showSubscriptions(HttpSession session, Model model) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        List<SubscriptionDTO> subscriptionDTOS = subscriptionClient.getAllSubscriptions(AUTH_HEADER + token2);
        model.addAttribute("subscriptions", subscriptionDTOS);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/all_subscriptions";
    }

    @GetMapping("/villages/latin-names")
    public String translateVillagesNamesToLatin(HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        String result = adminFunctionClient.translateVillagesNamesToLatin(AUTH_HEADER + token).getBody();
        redirectAttributes.addFlashAttribute(MESSAGE, result);
        return "redirect:/admins/village";
    }
    @GetMapping("/messages")
    public String showUserMessages(HttpSession session, Model model) {
        String token = (String) session.getAttribute(SESSION_NAME);
        List<MessageDTO> messages = messageClient.getAllMessages(AUTH_HEADER + token);
        model.addAttribute("messages", messages);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/user_messages";
    }
    @GetMapping("/inquiries")
    public String showUserInquiries(HttpSession session, Model model) {
        String token = (String) session.getAttribute(SESSION_NAME);
        List<InquiryDTO> inquiries = inquiryClient.getAllInquiries(AUTH_HEADER + token);
        inquiries.forEach(inquiryDTO -> inquiryDTO.setVillageName(villageClient.getVillageById(inquiryDTO.getVillageId()).getName()));
        model.addAttribute("inquiries", inquiries);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/user_inquiries";
    }
    @GetMapping("/contacts")
    public String showUserContacts(HttpSession session, Model model) {
        String token = (String) session.getAttribute(SESSION_NAME);
        List<Object[]> answersWithVillageName = villageAnswerQuestionClient.findVillageNameAndAnswerByQuestionName("question_name.eighth", AUTH_HEADER + token);
        model.addAttribute("contacts", answersWithVillageName);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/user_contacts";
    }
    @GetMapping("/manage-videos/{villageId}")
    public String manageVideos(@PathVariable("villageId") Long villageId, Model model, HttpSession session) {
        VillageDTO villageDTO = villageClient.getVillageById(villageId);
        model.addAttribute("village", villageDTO);
        String token2 = (String) session.getAttribute(SESSION_NAME);
        List<VillageVideoDTO> villageVideoDTOList = villageVideoClient.getAllVideosByVillageId(villageId);//, AUTH_HEADER + token2
        System.out.println("video list " + villageVideoDTOList);
        model.addAttribute("villageVideoDTOs", villageVideoDTOList);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        return "admin_templates/admin_videos";
    }

    @PostMapping("/videos/villageId")
    public String saveAllVideos(@RequestParam("villageId") Long villageId, @RequestParam("videos") List<String> videosUrls, HttpSession session){
        List<VillageVideoDTO> villageVideoDTOList = videosUrls.stream()
                .map(videoUrl -> {
                    VillageVideoDTO videoDTO = new VillageVideoDTO();
                    videoDTO.setVillageId(villageId);
                    videoDTO.setUrl(videoUrl);
                    videoDTO.setStatus(true);
                    videoDTO.setDateUpload(now());
                    return videoDTO;
                })
                .toList();
        villageVideoClient.saveVideos(villageVideoDTOList);
        return "admin_templates/admin_videos";

    }
}