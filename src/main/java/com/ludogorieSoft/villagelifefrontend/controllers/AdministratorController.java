package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.AdminClient;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admins")
@AllArgsConstructor
public class AdministratorController {
    private AdminClient adminClient;
//    private final HttpHeaders headers;

    @GetMapping
    public String getAllAdmins(Model model, HttpSession session) {
        String token = (String) session.getAttribute("admin");
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcwMTM5NjIsImV4cCI6MTY4NzEwMDM2Mn0.32m49heFF9XkvO5wsGfmIxdpiDkuyU-2oibC9Oj03GA"; //(String) session.getAttribute("admin");

        System.out.println(token2);
//        String result = String.valueOf(adminClient.getAllAdministrators("Bearer " + token));
        ResponseEntity<List<AdministratorDTO>> administrators = adminClient.getAllAdministrators("Bearer " + token2);
//        System.out.println(adminClient.getAllAdministrators("Bearer " + token2));
        List<AdministratorDTO> allAdmins = administrators.getBody();
        model.addAttribute("admins", allAdmins);
        return "admin_templates/admin_all";
    }

    @GetMapping("/edit/{adminId}")
    public String editAdmin(@PathVariable(name = "adminId") Long adminId,Model model) {
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcwMTM5NjIsImV4cCI6MTY4NzEwMDM2Mn0.32m49heFF9XkvO5wsGfmIxdpiDkuyU-2oibC9Oj03GA"; //(String) session.getAttribute("admin");
        ResponseEntity<AdministratorDTO> adminById = adminClient.getAdministratorById(adminId,"Bearer " + token2);
        AdministratorDTO administratorDTO = adminById.getBody();
        model.addAttribute("admins", administratorDTO);
        return "admin_templates/update_admin";
    }

    @PutMapping("/update")
    public String updateAdmin(Long adminId,@ModelAttribute("admins") AdministratorRequest administratorRequest) {
        String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcwMTM5NjIsImV4cCI6MTY4NzEwMDM2Mn0.32m49heFF9XkvO5wsGfmIxdpiDkuyU-2oibC9Oj03GA"; //(String) session.getAttribute("admin");
        adminClient.updateAdministrator(adminId, administratorRequest,"Bearer " + token2);
        return "admin_templates/update_admin";
    }

    @GetMapping("/menu")
    public String showMenu(HttpSession session) {
        return "admin_templates/admin_menu";
    }
}