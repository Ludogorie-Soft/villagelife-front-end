package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AdministratorDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.request.AdministratorRequest;
import com.ludogorieSoft.villagelifefrontend.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AdministratorControllerTest {
    private HttpSession session;
    private AdminClient adminClient;
    private Model model;
    private AdministratorRequest administratorRequest;
    private  BindingResult bindingResult;
    private  RedirectAttributes redirectAttributes;
    @BeforeEach
    public void setUp() {
        session = mock(HttpSession.class);
        adminClient = mock(AdminClient.class);
        model = mock(Model.class);
        administratorRequest = mock(AdministratorRequest.class);
        bindingResult = mock(BindingResult.class);
        redirectAttributes = mock(RedirectAttributes.class);
    }
    @Test
     void testGetAllAdmins() {

        when(adminClient.getAllAdministrators(anyString())).thenReturn(ResponseEntity.ok(new ArrayList<>()));

        AdministratorController controller = new AdministratorController(adminClient, null);  // instance of Controller

        String viewName = controller.getAllAdmins(model, session);

        assertEquals("admin_templates/admin_all", viewName);
        verify(adminClient, times(1)).getAllAdministrators(anyString());
        verify(model, times(1)).addAttribute(eq("admins"), anyList());
    }
    @Test
     void testEditAdmin() {

        when(adminClient.getAdministratorById(anyLong(), anyString())).thenReturn(ResponseEntity.ok(new AdministratorDTO()));

        AdministratorController controller = new AdministratorController(adminClient, null);

        String viewName = controller.editAdmin(1L, model, session);

        assertEquals("admin_templates/update_admin", viewName);
        verify(adminClient, times(1)).getAdministratorById(eq(1L), anyString());
        verify(model, times(1)).addAttribute(eq("admins"), any(AdministratorDTO.class));
    }

    @Test
     void testUpdateAdmin() {

        AdministratorController controller = new AdministratorController(adminClient, null);

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String viewName = controller.updateAdmin(1L, administratorRequest, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/admins", viewName);
        verify(bindingResult, times(1)).hasErrors();
        verify(administratorRequest, times(1)).setCreatedAt(any(LocalDateTime.class));
        verify(administratorRequest, times(1)).setRole(Role.ADMIN);
        verify(adminClient, times(1)).updateAdministrator(eq(1L), eq(administratorRequest), anyString());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("message"), anyString());
    }
    @Test
     void testDeleteAdmin() {

        doNothing().when(adminClient).deleteAdministratorById(anyLong(), anyString());

        AdministratorController controller = new AdministratorController(adminClient, null);

        ModelAndView modelAndView = controller.deleteAdmin(1L, redirectAttributes, session);

        assertEquals("redirect:/admins", modelAndView.getViewName());
        verify(adminClient, times(1)).deleteAdministratorById(eq(1L), anyString());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("message"), anyString());
    }



}