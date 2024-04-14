package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDTO.class.equals(clazz);
    }
    private static final String FIELD_REQUIRED = "field.required";
    private final ValidationUtilsClient validationUtilsClient;

    @Override
    public void validate(Object target, Errors errors) {
        MessageDTO messageDTO = (MessageDTO) target;

        if (messageDTO.getEmail() != null && messageDTO.getEmail().trim().equals("")) {
            errors.rejectValue("email", FIELD_REQUIRED, "email.required");
        }
        if (messageDTO.getUserMessage() != null && messageDTO.getUserMessage().trim().equals("")) {
            errors.rejectValue("userMessage", FIELD_REQUIRED, "message.required");
        }
        if (messageDTO.getUserName() != null && messageDTO.getUserName().trim().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "username.minlength");
        } else if (Boolean.FALSE.equals(validationUtilsClient.usernameCheck(messageDTO.getUserName()))) {
            errors.rejectValue("userName", FIELD_REQUIRED, "username.lettersOnly");
        }
    }
}
