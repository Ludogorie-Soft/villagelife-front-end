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
    private static final String FIELD_MIN_LENGTH = "field.minlength";

    @Override
    public boolean supports(Class<?> clazz) {
        return BusinessCardDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BusinessCardDTO businessCardDTO = (BusinessCardDTO) target;
        if (businessCardDTO.getName() == null)
            errors.rejectValue("businessCardDTO.name", FIELD_MIN_LENGTH, "Business name can not be empty!!!");
        if (businessCardDTO.getEmail() == null)
            errors.rejectValue("businessCardDTO.email", FIELD_MIN_LENGTH, "Business email can not be empty!!!");
        if (businessCardDTO.getPhoneNumber() == null)
            errors.rejectValue("businessCardDTO.phoneNumber", FIELD_MIN_LENGTH, "Business phone number can not be empty!!!");
        if (businessCardDTO.getAddress() == null)
            errors.rejectValue("businessCardDTO.address", FIELD_MIN_LENGTH, "Business address can not be empty!!!");
        if (businessCardDTO.getNumberOfEmployees() < 0)
            errors.rejectValue("businessCardDTO.numberOfEmployees", FIELD_MIN_LENGTH, "Number of employees can not be less than 0!!!");
    }
}
