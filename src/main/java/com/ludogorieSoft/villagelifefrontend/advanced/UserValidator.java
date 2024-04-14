package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {

    private static final String FIELD_REQUIRED = "field.required";

    private final ValidationUtilsClient validationUtilsClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (userDTO.getFullName() == null || userDTO.getFullName().trim().length() < 6) {
            errors.rejectValue("userDTO.fullName", "field.minlength", "names.required");
        } else if (Boolean.FALSE.equals(validationUtilsClient.usernameCheck(userDTO.getFullName()))) {
            errors.rejectValue("userDTO.fullName", FIELD_REQUIRED, "username.lettersOnly");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().length() < 9) {
            errors.rejectValue("userDTO.email", "field.minlength", "email.required");
        }
        if(!userDTO.isConsent()){
            errors.rejectValue("userDTO.consent", FIELD_REQUIRED,"consent.required");
        }
    }
}