package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;


@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdministratorController {
    private final AdminClient adminClient;
    private final VillageController villageController;

    private static final String SESSION_NAME = "admin";
    private static final String AUTH_HEATHER = "Bearer ";
    private static final String ADMINS = "admins";
    private static final String MESSAGE = "message";
    private static final String VILLAGES_ATTRIBUTE = "villages";

    @GetMapping
    public String getAllAdmins(Model model, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        ResponseEntity<List<AdministratorDTO>> administrators = adminClient.getAllAdministrators(AUTH_HEATHER + token2);
        List<AdministratorDTO> allAdmins = administrators.getBody();
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute("admin", administratorDTO.getFullName());//adminService.extractUsername(token2)
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
        adminClient.deleteVillageById(villageId, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Village with ID: " + villageId + " successfully deleted !!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/show/{villageId}")
    public String seeVillageToApproveIt(@RequestParam("villageId") Long villageId,
                                        @RequestParam("answerDate") String answerDate, Model model, HttpSession session) {
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        boolean status = false;
        String token2 = (String) session.getAttribute(SESSION_NAME);
        VillageInfo villageInfo = adminClient.getVillageInfoById(villageId, answerDate,status,AUTH_HEATHER + token2);
        InquiryDTO inquiryDTO = new InquiryDTO();
        villageController.getInfoForShowingVillage(villageInfo, inquiryDTO, status, answerDate, model, administratorDTO);
        return "ShowVillageById";
    }

    @PostMapping("/approve/{villageId}")
    public ModelAndView approveVillageResponse(@RequestParam("villageId") Long villageId,
                                            @RequestParam("answerDate") String answerDate, RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminClient.changeVillageStatus(villageId,answerDate, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Status of village with ID: " + villageId + " changed successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }

    @GetMapping("/village")
    public String findUnapprovedVillageResponseByVillageId(Model model,HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        try {
            ResponseEntity<List<VillageResponse>> villageResponses = adminClient.findUnapprovedVillageResponseByVillageId(AUTH_HEATHER + token2);

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
        adminClient.rejectVillageResponse(villageId,answerDate, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Response of village with ID: " + villageId + " rejected successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }

}