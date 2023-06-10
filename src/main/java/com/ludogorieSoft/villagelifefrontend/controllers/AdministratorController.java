package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admins")
@AllArgsConstructor
public class AdministratorController {
    private AdminClient adminClient;

    @GetMapping("/register")
    public String createAdministrator(Model model) {
        model.addAttribute("admin", new AdministratorRequest());
        return "admin_templates/register_form";
    }

    @PostMapping("/save")
    public ModelAndView createAdministrator(@Valid AdministratorRequest administratorRequest, BindingResult bindingResult){
        System.out.println("adminsssssss 1 " + administratorRequest);
        if(bindingResult.hasErrors()){
            return new ModelAndView("admin_templates/register_form");
        }
        System.out.println("adminsssssss 2 " + administratorRequest);

        adminClient.createAdministrator(administratorRequest);
        System.out.println("adminsssssss 3 " + administratorRequest);

        return new ModelAndView("redirect:/admin???????????????");
    }
}