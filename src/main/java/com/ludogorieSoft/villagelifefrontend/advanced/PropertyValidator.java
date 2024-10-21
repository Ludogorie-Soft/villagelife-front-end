package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class PropertyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PropertyDTO.class.equals(clazz);
    }
    private static final String FIELD_REQUIRED = "field.required";
    private final ValidationUtilsClient validationUtilsClient;
    @Override
    public void validate(Object target, Errors errors) {
        PropertyDTO propertyDTO = (PropertyDTO) target;

        if (propertyDTO.getPrice() == null || propertyDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("price", "price.invalid", "The price must be greater than or equal to 0 and cannot be empty.");
        }

        if (propertyDTO.getBuildUpArea() < 0) {
            errors.rejectValue("buildUpArea", "buildUpArea.invalid", "Build-up area must be greater than or equal to 0.");
        }

        if (propertyDTO.getYardArea() < 0) {
            errors.rejectValue("yardArea", "yardArea.invalid", "Yard area must be greater than or equal to 0.");
        }

        if (propertyDTO.getRoomsCount() < 0) {
            errors.rejectValue("roomsCount", "roomsCount.invalid", "The number of rooms must be greater than or equal to 0.");
        }

        if (propertyDTO.getBathroomsCount() < 0) {
            errors.rejectValue("bathroomsCount", "bathroomsCount.invalid", "The number of bathrooms must be greater than or equal to 0.");
        }

        if (propertyDTO.getPhoneNumber() == null || propertyDTO.getPhoneNumber().trim().length() < 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.invalid", "Phone number must be at least 10 digits long.");
        } else if (Boolean.FALSE.equals(validationUtilsClient.numberCheck(propertyDTO.getPhoneNumber()))) {
            errors.rejectValue("phoneNumber", "phoneNumber.numbersonly", "Phone number must contain only digits.");
        }

    }
}
