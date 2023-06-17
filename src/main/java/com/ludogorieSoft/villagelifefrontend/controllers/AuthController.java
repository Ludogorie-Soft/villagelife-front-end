package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.auth.AuthClient;
import com.ludogoriesoft.villagelifefrontend.auth.AuthenticationRequest;
import com.ludogoriesoft.villagelifefrontend.auth.AuthenticationResponce;
import com.ludogoriesoft.villagelifefrontend.auth.RegisterRequest;
import com.ludogoriesoft.villagelifefrontend.dtos.AdministratorRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
//    private final HttpHeaders headers;

    @GetMapping("/register")
    public String createAdministrator(Model model, HttpSession session) {
        model.addAttribute("admins", new AdministratorRequest());
        return "admin_templates/register_form";
    }
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute("admins") RegisterRequest request, HttpSession session) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWRrYTMiLCJpYXQiOjE2ODcwMTM5NjIsImV4cCI6MTY4NzEwMDM2Mn0.32m49heFF9XkvO5wsGfmIxdpiDkuyU-2oibC9Oj03GA";
        session.setAttribute("admin", token);
        //(String) session.getAttribute("admin");
        authClient.register(request,"Bearer " + token);
        return "admin_templates/admin_menu";
    }

    @GetMapping("/login")
    public String showAdminLogin(Model model) {
        model.addAttribute("admins",new AuthenticationRequest());
        return "admin_templates/admin_login";
    }

    @PostMapping("/authenticate")
    public String  authenticateAdmin(@ModelAttribute("admins") AuthenticationRequest request, HttpSession session) {
        ResponseEntity<AuthenticationResponce> authResponse = authClient.authenticate(request);
        String token = authResponse.getBody().getToken();
        session.setAttribute("admin", token);
       return "admin_templates/admin_menu";
    }

}
