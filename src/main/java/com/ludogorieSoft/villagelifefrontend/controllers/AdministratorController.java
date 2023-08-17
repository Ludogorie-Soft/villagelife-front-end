package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.config.AdminFunctionClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageImageClient;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageInfo;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import com.ludogorieSoft.villagelifefrontend.enums.Role;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    private static final String AUTH_HEATHER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String MESSAGE = "message";
    private static final String VILLAGES_ATTRIBUTE = "villages";
    private final VillageClient villageClient;
    private final VillageImageClient villageImageClient;

    @GetMapping
    public String getAllAdmins(Model model, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        ResponseEntity<List<AdministratorDTO>> administrators = adminClient.getAllAdministrators(AUTH_HEATHER + token2);
        List<AdministratorDTO> allAdmins = administrators.getBody();
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(SESSION_NAME, administratorDTO.getFullName());
        model.addAttribute(ADMINS, allAdmins);
        return "admin_templates/admin_all";
    }

    @GetMapping("/edit/{adminId}")
    public String editAdmin(@PathVariable(name = "adminId") Long adminId, Model model, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        ResponseEntity<AdministratorDTO> adminById = adminClient.getAdministratorById(adminId, AUTH_HEATHER + token2);
        AdministratorDTO administratorDTO = adminById.getBody();
        model.addAttribute(ADMINS, administratorDTO);
        model.addAttribute("roles", Role.ADMIN);
        return "admin_templates/update_admin";
    }

    @PostMapping("/update/{id}")
    public String updateAdmin(@PathVariable("id") Long adminId,
                              @Valid @ModelAttribute("admins") AdministratorRequest administratorRequest,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              HttpSession session, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.ADMIN);
            return "admin_templates/update_admin";
        }
        administratorRequest.setCreatedAt(now());

        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminClient.updateAdministrator(adminId, administratorRequest, AUTH_HEATHER + token2);

        redirectAttributes.addFlashAttribute(MESSAGE, "Administrator with ID: " + adminId + " successfully updated !!!");
        return "redirect:/admins";
    }

    @PostMapping("/delete/{adminId}")
    public ModelAndView deleteAdmin(@PathVariable(name = "adminId") Long adminId,
                                    RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminClient.deleteAdministratorById(adminId, AUTH_HEATHER + token2);
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
        adminFunctionClient.deleteVillageById(villageId, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Village with ID: " + villageId + " successfully deleted !!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/show/{villageId}")
    public String seeVillageToApproveIt(@RequestParam("villageId") Long villageId,
                                        @RequestParam("answerDate") String answerDate, Model model, HttpSession session) {
        model.addAttribute("subscription", new SubscriptionDTO());
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        boolean status = false;
        String token2 = (String) session.getAttribute(SESSION_NAME);
        VillageInfo villageInfo = adminFunctionClient.getVillageInfoById(villageId, answerDate,status,AUTH_HEATHER + token2);
        InquiryDTO inquiryDTO = new InquiryDTO();
        villageController.getInfoForShowingVillage(villageInfo, inquiryDTO, status, answerDate, model, administratorDTO);
        return "ShowVillageById";
    }
    @GetMapping("/manage-images/{villageId}")
    public String manageImages(@PathVariable("villageId") Long villageId, Model model, HttpSession session) {
        VillageDTO villageDTO = villageClient.getVillageById(villageId);
        model.addAttribute("village", villageDTO);
        List<VillageImageDTO> villageImageDTOs = villageImageClient.getNotDeletedVillageImageDTOsByVillageId(villageId);
        model.addAttribute("villageImageDTOs", villageImageDTOs);
        return "admin_templates/admin_images";
    }
    @GetMapping("/image-reject/{villageImageId}")
    public String rejectImage(@PathVariable("villageImageId") Long villageImageId){
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        villageImageClient.rejectVillageImage(villageImageId);
        return "redirect:/admins/manage-images/" + villageImageDTO.getVillageId() + "?villageId=" + villageImageDTO.getVillageId();
    }

    @PostMapping("/save-images")
    public String saveVillage(@RequestParam("newImages") List<MultipartFile> images,
                              @RequestParam("villageId") Long villageId) {
        List<byte[]> imageBytes = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                byte[] imageData = image.getBytes();
                imageBytes.add(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        villageImageClient.adminUploadImages(villageId, imageBytes);
        return "redirect:/admins/manage-images/" + villageId + "?villageId=" + villageId;
    }

    @PostMapping("/approve/{villageId}")
    public ModelAndView approveVillageResponse(@RequestParam("villageId") Long villageId,
                                            @RequestParam("answerDate") String answerDate, RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminFunctionClient.changeVillageStatus(villageId,answerDate, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Status of village with ID: " + villageId + " changed successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/village")
    public String findUnapprovedVillageResponseByVillageId(Model model,HttpSession session) {
        model.addAttribute("subscription", new SubscriptionDTO());
        String token2 = (String) session.getAttribute(SESSION_NAME);
        try {
            ResponseEntity<List<VillageResponse>> villageResponses = adminFunctionClient.findUnapprovedVillageResponseByVillageId(AUTH_HEATHER + token2);

            if (villageResponses.getStatusCode().is2xxSuccessful()) {
                AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
                model.addAttribute(ADMINS, administratorDTO.getFullName());
                List<VillageResponse> villages = villageResponses.getBody();
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
        adminFunctionClient.rejectVillageResponse(villageId,answerDate, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Response of village with ID: " + villageId + " rejected successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }
    @GetMapping("/deleted-images/{villageId}")
    public String getDeletedImages(@PathVariable("villageId") Long villageId, Model model, HttpSession session) {
        VillageDTO villageDTO = villageClient.getVillageById(villageId);
        model.addAttribute("village", villageDTO);
        List<VillageImageDTO> villageImageDTOs = villageImageClient.getDeletedVillageImageDTOsByVillageId(villageId);
        model.addAttribute("villageImageDTOs", villageImageDTOs);
        return "admin_templates/deleted_images";
    }

    @GetMapping("/delete/{villageImageId}")
    public String deleteImage(@PathVariable("villageImageId") Long villageImageId) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        Long villageId = villageImageDTO.getVillageId();
        villageImageClient.deleteImageFileById(villageImageId);
        return "redirect:/admins/deleted-images/" + villageId;
    }

    @GetMapping("/resume/{villageImageId}")
    public String resumeImage(@PathVariable("villageImageId") Long villageImageId) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        Long villageId = villageImageDTO.getVillageId();
        villageImageClient.resumeImageVillageById(villageImageId);
        return "redirect:/admins/deleted-images/" + villageId;
    }

    @GetMapping("/approve-image/{villageImageId}")
    public String approveImage(@PathVariable("villageImageId") Long villageImageId) {
        VillageImageDTO villageImageDTO = villageImageClient.getVillageImageById(villageImageId);
        villageImageDTO.setStatus(true);
        villageImageClient.updateVillageImage(villageImageId, villageImageDTO);
        return "redirect:/admins/manage-images/" + villageImageDTO.getVillageId();
    }
}