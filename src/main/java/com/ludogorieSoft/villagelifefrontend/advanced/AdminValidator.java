package com.ludogorieSoft.villagelifefrontend.advanced;


import com.ludogorieSoft.villagelifefrontend.dtos.request.RegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class AdminValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "field.required", "Въведете две имена");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Имейлът е задължителен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "Потребителското име е задължително");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "field.required", "Телефонния номер е задължителен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Паролата е задължителна");

        if (registerRequest.getFullName() != null && registerRequest.getFullName().length() < 2) {
            errors.rejectValue("fullName", "field.minlength", "Името трябва да бъде поне 2 символа");
        }

        if (registerRequest.getUsername() != null && registerRequest.getUsername().length() < 5) {
            errors.rejectValue("username", "field.minlength", "Потребителското име трябва да бъде поне 5 символа");
        }
        if (registerRequest.getMobile() != null && registerRequest.getMobile().length() < 10) {
            errors.rejectValue("mobile", "field.minlength", "Телефонният номер трябва да бъде поне 10 символа");
        }
        if (registerRequest.getPassword() != null && registerRequest.getPassword().length() < 10) {
            errors.rejectValue("password", "field.minlength", "Паролата трябва да бъде поне 10 символа");
        }

    }

}
