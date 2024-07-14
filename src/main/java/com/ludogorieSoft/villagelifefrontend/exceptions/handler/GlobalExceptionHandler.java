package com.ludogorieSoft.villagelifefrontend.exceptions.handler;

import com.ludogorieSoft.villagelifefrontend.exceptions.*;
import com.ludogorieSoft.villagelifefrontend.slack.SlackMessage;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private SlackMessage slackMessage;
    private static final String MESSAGE = "message";
    private static final String LOGIN_URL = "redirect:/auth/login";
    private static final String ERROR_URL = "/admin_templates/error";

    @ExceptionHandler(Exception.class)
    public void alertSlackChannelWhenUnhandledExceptionOccurs(Exception ex) {
        slackMessage.publishMessage("villagelife-notifications",
                "Error occured from the frontend application ->" + ex.getMessage());
    }

    @ExceptionHandler(NoConsentException.class)
    public ModelAndView handleNoConsentException(NoConsentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("consentMessage", ex.getMessage());
        return new ModelAndView("/add-village");
    }

    @ExceptionHandler(ApiRequestException.class)
    public ModelAndView handleApiRequestException(ApiRequestException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView(LOGIN_URL);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ModelAndView handleTokenExpiredException(TokenExpiredException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView(LOGIN_URL);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView handleDuplicateEmailException(DuplicateEmailException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView("redirect:/auth/register");
    }
    @ExceptionHandler({ImageMaxUploadSizeExceededException.class,MaxUploadSizeExceededException.class})
    public ModelAndView handleImageMaxUploadSizeExceededException(Model model) {
        model.addAttribute("errorMessage", "File size should not exceed 5 MB.");
        return new ModelAndView(ERROR_URL);
    }
    @ExceptionHandler(SocketTimeoutException.class)
    public ModelAndView handleSocketTimeoutException(SocketTimeoutException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView(ERROR_URL);
    }
    @ExceptionHandler(UsernamePasswordException.class)
    public ModelAndView handleUsernamePasswordException(UsernamePasswordException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView(LOGIN_URL);
    }
    @ExceptionHandler(SitemapGeneratorException.class)
    public ModelAndView handleSitemapGeneratorException(SitemapGeneratorException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView(ERROR_URL);
    }
    @ExceptionHandler(MalformedURLException.class)
    public ModelAndView handleMalformedURLException(MalformedURLException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        return new ModelAndView(ERROR_URL);
    }
}

