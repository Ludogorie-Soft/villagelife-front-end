package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.dtos.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDTO.class.equals(clazz);
    }
    private static final String FIELD_REQUIRED = "field.required";

    @Override
    public void validate(Object target, Errors errors) {
        MessageDTO messageDTO = (MessageDTO) target;

        if (messageDTO.getEmail() != null && messageDTO.getEmail().trim().equals("")) {
            errors.rejectValue("email", FIELD_REQUIRED, "Имейлът е задължителен");
        }
        if (messageDTO.getUserMessage() != null && messageDTO.getUserMessage().trim().equals("")) {
            errors.rejectValue("userMessage", FIELD_REQUIRED, "Съобщението е задължително");
        }
        if (messageDTO.getUserName() != null && messageDTO.getUserName().trim().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "Името трябва да бъде поне 2 символа");
        }
    }
}
