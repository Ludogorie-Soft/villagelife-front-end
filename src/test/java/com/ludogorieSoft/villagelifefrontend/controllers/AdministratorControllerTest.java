package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.AdminClient;
import com.ludogorieSoft.villagelifefrontend.config.AdminFunctionClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageImageClient;
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
    private BindingResult bindingResult;
    private RedirectAttributes redirectAttributes;
    private AdminFunctionClient adminFunctionClient;
    private VillageController villageController;
    private VillageClient villageClient;
    private VillageImageClient villageImageClient;
    @BeforeEach
    public void setUp() {
        session = mock(HttpSession.class);
        adminClient = mock(AdminClient.class);
        model = mock(Model.class);
        administratorRequest = mock(AdministratorRequest.class);
        bindingResult = mock(BindingResult.class);
        redirectAttributes = mock(RedirectAttributes.class);
        adminFunctionClient = mock(AdminFunctionClient.class);
        villageController = mock(VillageController.class);
        villageImageClient = mock(VillageImageClient.class);
    }
    @Test
     void testEditAdmin() {

        when(adminClient.getAdministratorById(anyLong(), anyString())).thenReturn(ResponseEntity.ok(new AdministratorDTO()));

        AdministratorController controller = new AdministratorController(adminFunctionClient,adminClient, villageController, villageClient, villageImageClient);

        String viewName = controller.editAdmin(1L, model, session);

        assertEquals("admin_templates/update_admin", viewName);
        verify(adminClient, times(1)).getAdministratorById(eq(1L), anyString());
        verify(model, times(1)).addAttribute(eq("admins"), any(AdministratorDTO.class));
    }

    @Test
     void testDeleteAdmin() {

        doNothing().when(adminClient).deleteAdministratorById(anyLong(), anyString());

        AdministratorController controller = new AdministratorController(adminFunctionClient,adminClient, villageController, villageClient, villageImageClient);

        ModelAndView modelAndView = controller.deleteAdmin(1L, redirectAttributes, session);

        assertEquals("redirect:/admins", modelAndView.getViewName());
        verify(adminClient, times(1)).deleteAdministratorById(eq(1L), anyString());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("message"), anyString());
    }



}