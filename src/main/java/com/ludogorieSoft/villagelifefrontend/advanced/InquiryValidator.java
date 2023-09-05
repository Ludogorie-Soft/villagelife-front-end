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
    private static final String FIELD_REQUIRED = "field.required";
    @Override
    public void validate(Object target, Errors errors) {
        InquiryDTO inquiryDTO = (InquiryDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inquiryType", FIELD_REQUIRED, "Изберете тип на запитването");

        if (inquiryDTO.getUserMessage() != null && inquiryDTO.getUserMessage().trim().equals("")) {
            errors.rejectValue("userMessage", FIELD_REQUIRED, "Съобщението е задължително");
        }

        if (inquiryDTO.getEmail() != null && inquiryDTO.getEmail().trim().equals("")) {
            errors.rejectValue("email", FIELD_REQUIRED, "Имейлът е задължителен");
        }

        if (inquiryDTO.getUserName() != null && inquiryDTO.getUserName().trim().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "Името трябва да бъде поне 2 символа");
        }

        if (inquiryDTO.getMobile() != null && inquiryDTO.getMobile().trim().length() < 10) {
            errors.rejectValue("mobile", "field.minlength", "Телефонният номер трябва да бъде поне 10 символа");
        }
        if(!inquiryDTO.isHasAgreed()){
            errors.rejectValue("hasAgreed", FIELD_REQUIRED);
        }
    }
}