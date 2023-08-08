package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.dtos.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MessageDTO messageDTO = (MessageDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Имейлът е задължителен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userMessage", "field.required", "Съобщението е задължително");

        if (messageDTO.getUserName() != null && messageDTO.getUserName().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "Името трябва да бъде поне 2 символа");
        }
    }
}
