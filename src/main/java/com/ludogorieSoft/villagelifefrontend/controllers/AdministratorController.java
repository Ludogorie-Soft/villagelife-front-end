package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.AdminClient;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorRequest;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
import com.ludogoriesoft.villagelifefrontend.enums.Role;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/admins")
@AllArgsConstructor
public class AdministratorController {
    private AdminClient adminClient;
    private ModelMapper modelMapper;
//    private final HttpHeaders headers;

    @GetMapping("/menu")
    public String showMenu() {
        return "admin_templates/admin_menu";
    }
    @GetMapping
    public String getAllAdmins(Model model, HttpSession session) {
        //String token = (String) session.getAttribute("admin");
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcxMDU3NzAsImV4cCI6MTY4NzE5MjE3MH0.t6fqJKcCEnbq8BRZ8Arh1QdrPMIKLgsPyGoVK6GbwNw";

        ResponseEntity<List<AdministratorDTO>> administrators = adminClient.getAllAdministrators("Bearer " + token2);
        List<AdministratorDTO> allAdmins = administrators.getBody();
        model.addAttribute("admins", allAdmins);
        return "admin_templates/admin_all";
    }

    @GetMapping("/edit/{adminId}")
    public String editAdmin(@PathVariable(name = "adminId") Long adminId,Model model) {
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcxMDU3NzAsImV4cCI6MTY4NzE5MjE3MH0.t6fqJKcCEnbq8BRZ8Arh1QdrPMIKLgsPyGoVK6GbwNw"; //(String) session.getAttribute("admin");
        ResponseEntity<AdministratorDTO> adminById = adminClient.getAdministratorById(adminId,"Bearer " + token2);
        AdministratorDTO administratorDTO = adminById.getBody();
        model.addAttribute("admins", administratorDTO);
        return "admin_templates/update_admin";
    }
    @PostMapping("/update")
    public String updateAdmin(Long adminId,@ModelAttribute("admins") AdministratorDTO administratorDTO,RedirectAttributes redirectAttributes) {
        administratorDTO.setCreatedAt(LocalDateTime.now());
        administratorDTO.setEnabled(true);
        administratorDTO.setRole(Role.ADMIN);
        AdministratorRequest administratorRequest =  modelMapper.map(administratorDTO, AdministratorRequest.class);
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcxMDU3NzAsImV4cCI6MTY4NzE5MjE3MH0.t6fqJKcCEnbq8BRZ8Arh1QdrPMIKLgsPyGoVK6GbwNw"; //(String) session.getAttribute("admin");
        adminClient.updateAdministrator(adminId, administratorRequest,"Bearer " + token2);
        redirectAttributes.addFlashAttribute("message", "Administrator with ID: " + adminId + " successfully updated !!!");
        return "redirect:/admins";
    }
    @PostMapping("/delete/{adminId}")
    public ModelAndView deleteAdmin(@PathVariable(name = "adminId") Long adminId, RedirectAttributes redirectAttributes){
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcxMDU3NzAsImV4cCI6MTY4NzE5MjE3MH0.t6fqJKcCEnbq8BRZ8Arh1QdrPMIKLgsPyGoVK6GbwNw"; //(String) session.getAttribute("admin");
        adminClient.deleteAdministratorById(adminId,"Bearer " + token2);
        redirectAttributes.addFlashAttribute("message", "Administrator with ID: " + adminId + " successfully deleted !!!");
        return new ModelAndView("redirect:/admins");

    }
    @GetMapping("/village")
    public String getAllVillages(Model model){
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcxMDU3NzAsImV4cCI6MTY4NzE5MjE3MH0.t6fqJKcCEnbq8BRZ8Arh1QdrPMIKLgsPyGoVK6GbwNw";
        model.addAttribute("villages", adminClient.getAllVillages("Bearer " + token2));
        return "admin_templates/admin_menu";
    }
}