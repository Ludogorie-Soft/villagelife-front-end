package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.dtos.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userDTO.fullName", "field.required", "Имената са задължителни");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userDTO.email", "field.required", "Имейлът е задължителен");

        if (userDTO.getFullName() != null && userDTO.getFullName().length() < 6) {
            errors.rejectValue("userDTO.fullName", "field.minlength", "Попълнете име и фамилия");
        }

        if (userDTO.getEmail() != null && userDTO.getEmail().length() < 9) {
            errors.rejectValue("userDTO.email", "field.minlength", "Въведете валиден имейл");
        }
        if(!userDTO.isConsent()){
            errors.rejectValue("userDTO.consent", "field.required","Трябва да се съгласите с условията");
        }
    }
}