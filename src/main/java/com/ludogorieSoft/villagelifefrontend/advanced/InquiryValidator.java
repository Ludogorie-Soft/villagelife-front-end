package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class InquiryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return InquiryDTO.class.equals(clazz);
    }
    private static final String FIELD_REQUIRED = "field.required";
    private final ValidationUtilsClient validationUtilsClient;
    @Override
    public void validate(Object target, Errors errors) {
        InquiryDTO inquiryDTO = (InquiryDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inquiryType", FIELD_REQUIRED, "inquiry.type.select");

        if (inquiryDTO.getUserMessage() == null || inquiryDTO.getUserMessage().trim().equals("")) {
            errors.rejectValue("userMessage", FIELD_REQUIRED, "message.required");
        }

        if (inquiryDTO.getEmail() == null || inquiryDTO.getEmail().trim().equals("")) {
            errors.rejectValue("email", FIELD_REQUIRED, "email.required");
        }

        if (inquiryDTO.getUserName() == null || inquiryDTO.getUserName().trim().length() < 2) {
            errors.rejectValue("userName", "field.minlength", "username.minlength");
        } else if (Boolean.FALSE.equals(validationUtilsClient.usernameCheck(inquiryDTO.getUserName()))) {
            errors.rejectValue("userName", FIELD_REQUIRED, "username.lettersOnly");
        }

        if (inquiryDTO.getMobile() == null || inquiryDTO.getMobile().trim().length() < 10) {
            errors.rejectValue("mobile", "field.minlength", "phone.minlength");
        } else if (Boolean.FALSE.equals(validationUtilsClient.numberCheck(inquiryDTO.getMobile()))) {
            errors.rejectValue("mobile", FIELD_REQUIRED, "phone.numbersonly");
        }
        if(!inquiryDTO.isHasAgreed()){
            errors.rejectValue("hasAgreed", FIELD_REQUIRED);
        }
    }
}