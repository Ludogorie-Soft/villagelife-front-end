package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InquiryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return InquiryDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InquiryDTO inquiryDTO = (InquiryDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Имейлът е задължителен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userMessage", "field.required", "Съобщението е задължително");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inquiryType", "field.required", "Изберете тип на запитването");

        if (inquiryDTO.getUserName() != null && inquiryDTO.getUserName().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "Името трябва да бъде поне 2 символа");
        }

        if (inquiryDTO.getEmail() != null && inquiryDTO.getMobile().length() < 10) {
            errors.rejectValue("mobile", "field.minlength", "Телефонният номер трябва да бъде поне 10 символа");
        }
        if(!inquiryDTO.isHasAgreed()){
            errors.rejectValue("hasAgreed", "field.required");
        }
    }
}