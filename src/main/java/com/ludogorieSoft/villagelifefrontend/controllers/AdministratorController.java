package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AddVillageFormResult;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.dtos.response.VillageResponse;
import com.ludogorieSoft.villagelifefrontend.enums.Role;

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

    @GetMapping("/village")
    public String getAllVillages(Model model, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        List<VillageResponse> villages = adminClient.getAllVillages(AUTH_HEATHER + token2);
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        model.addAttribute(ADMINS, administratorDTO.getFullName());
        model.addAttribute("villages", villages);
        return "admin_templates/admin_menu";
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
    public String seeVillageToApproveIt(@PathVariable(name = "villageId") Long id, Model model, HttpSession session) {
        AdministratorDTO administratorDTO = (AdministratorDTO) session.getAttribute("info");
        villageController.getTablesVillageById(id, model, administratorDTO);
        return "ShowVillageById";
    }

    @PostMapping("/approve/{id}")
    public ModelAndView changeVillageStatus(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        String token2 = (String) session.getAttribute(SESSION_NAME);
        adminClient.changeVillageStatus(id, AUTH_HEATHER + token2);
        redirectAttributes.addFlashAttribute(MESSAGE, "Status of village with ID: " + id + " changed successfully!!!");
        return new ModelAndView("redirect:/admins/village");
    }
//    @GetMapping("/update/{villageId}")
//    public String showCreateVillageForm(@PathVariable(name = "villageId")Long id, Model model,HttpSession session) {
//        String token2 = (String) session.getAttribute(SESSION_NAME);
////        adminClient.getVillageById(id, AUTH_HEATHER + token2);
//        AddVillageFormResult addVillageFormResult = new AddVillageFormResult();
//        villageController.addAllListsWithOptions(model);
//        model.addAttribute("addVillageFormResult", addVillageFormResult);
//        return "add-village";
//    }


}