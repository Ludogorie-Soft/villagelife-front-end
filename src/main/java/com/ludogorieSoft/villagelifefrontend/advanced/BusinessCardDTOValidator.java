package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.BusinessCardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class BusinessCardDTOValidator implements Validator {
    private static final String FIELD_REQUIRED = "field.required";

    private final ValidationUtilsClient validationUtilsClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return BusinessCardDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BusinessCardDTO businessCardDTO = (BusinessCardDTO) target;
        if (businessCardDTO.getName() == null)
            errors.rejectValue("businessCardDTO.name", "field.minlength", "Business name can not be empty!!!");
        if (businessCardDTO.getEmail() == null)
            errors.rejectValue("businessCardDTO.email", "field.minlength", "Business email can not be empty!!!");
        if (businessCardDTO.getPhoneNumber() == null)
            errors.rejectValue("businessCardDTO.phoneNumber", "field.minlength", "Business phone number can not be empty!!!");
        if (businessCardDTO.getAddress() == null)
            errors.rejectValue("businessCardDTO.address", "field.minlength", "Business address can not be empty!!!");
        if (businessCardDTO.getNumberOfEmployees() < 0)
            errors.rejectValue("businessCardDTO.numberOfEmployees", "field.minlength", "NUmber of employees can not be less than 0!!!");
    }
}
