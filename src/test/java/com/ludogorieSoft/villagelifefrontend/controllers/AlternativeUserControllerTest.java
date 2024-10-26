package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.config.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;


import static org.mockito.Mockito.*;

class AlternativeUserControllerTest {
    private HttpSession session;
    private AlternativeUserClient alternativeUserClient;
    private Model model;
    private RedirectAttributes redirectAttributes;
    private AdminFunctionClient adminFunctionClient;
    private VillageController villageController;

    private VillageClient villageClient;
    private VillageImageClient villageImageClient;
    private SubscriptionClient subscriptionClient;
    @BeforeEach
    public void setUp() {
        session = mock(HttpSession.class);
        alternativeUserClient = mock(AlternativeUserClient.class);
        model = mock(Model.class);
        redirectAttributes = mock(RedirectAttributes.class);
        adminFunctionClient = mock(AdminFunctionClient.class);
        villageController = mock(VillageController.class);
        villageImageClient = mock(VillageImageClient.class);
        subscriptionClient = mock(SubscriptionClient.class);
    }
}