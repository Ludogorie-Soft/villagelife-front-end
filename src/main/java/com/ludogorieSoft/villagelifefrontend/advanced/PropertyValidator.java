package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
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
        if (propertyDTO.getBuildUpArea() == null || propertyDTO.getBuildUpArea() < 0) {
            errors.rejectValue("buildUpArea", "buildUpArea.invalid", "Build-up area must be greater than or equal to 0.");
        }

        if (propertyDTO.getYardArea() == null || propertyDTO.getYardArea() < 0) {
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
        if (propertyDTO.getPropertyType() == null) {
            errors.rejectValue("propertyType", "propertyType.invalid", "You must select a valid property type.");
        }
        if (propertyDTO.getOwnershipType() == null) {
            errors.rejectValue("ownershipType", "ownershipType.invalid", "You must select a valid ownership type.");
        }
        if (propertyDTO.getPropertyCondition() == null) {
            errors.rejectValue("propertyCondition", "propertyCondition.invalid", "You must select a valid property condition.");
        }
        if (propertyDTO.getDescription() == null || propertyDTO.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "description.invalid", "Description is required and cannot be blank.");
        } else if (propertyDTO.getDescription().length() < 10 || propertyDTO.getDescription().length() > 500) {
            errors.rejectValue("description", "description.size", "Description must be between 10 and 500 characters long.");
        }
        if (propertyDTO.getAddress() == null || propertyDTO.getAddress().trim().isEmpty()) {
            errors.rejectValue("address", "address.invalid", "Address is required and cannot be blank.");
        }
        if (propertyDTO.getVillageDTO().getName() == null || propertyDTO.getVillageDTO().getName().trim().isEmpty()) {
            errors.rejectValue("villageDTO.name", "villageDTO.name.invalid", "Village name is required.");
        }
        if (propertyDTO.getVillageDTO().getRegion() == null || propertyDTO.getVillageDTO().getRegion().trim().isEmpty()) {
            errors.rejectValue("villageDTO.region", "villageDTO.region.invalid", "Village region is required.");
        }
        System.out.println("!" + propertyDTO.getImages().get(0) + "!");
        if (propertyDTO.getImages() == null || propertyDTO.getImages().isEmpty() || propertyDTO.getImages().get(0).getPropertyImageBytes() == null) {
            errors.rejectValue("images", "images.invalid", "At least one image is required.");
        } else {
            // Ограничение за размер на всяко изображение (500 KB)
            long totalSizeInKB = 0;
            for (int i = 0; i < propertyDTO.getImages().size(); i++) {
                PropertyImageDTO image = propertyDTO.getImages().get(i);

                // Проверка дали изображението не е празно
                if (image.getPropertyImageBytes() == null || image.getPropertyImageBytes().length == 0) {
                    errors.rejectValue("images[" + i + "].propertyImageBytes", "image.empty", "Image cannot be empty.");
                } else {
                    // Проверка за размер на изображението (500 KB = 512000 bytes)
                    long imageSizeInKB = image.getPropertyImageBytes().length / 1024;
                    totalSizeInKB += imageSizeInKB;

                    if (imageSizeInKB > 500) {
                        errors.rejectValue("images[" + i + "].propertyImageBytes", "image.size.exceeded", "Image size cannot exceed 500 KB.");
                    }

                    // Лимитиране на общия размер на всички изображения (например 10 MB = 10240 KB)
                    if (totalSizeInKB > 10240) {
                        errors.rejectValue("images", "images.totalSize.exceeded", "Total size of all images cannot exceed 10 MB.");
                        break; // Спиране на проверката, ако лимитът е надхвърлен
                    }
                }
            }
        }
        if (propertyDTO.getMainImageBytes() == null) {
            errors.rejectValue("mainImageBytes", "mainImageBytes.invalid", "Main image is required.");
        }
        else if(propertyDTO.getMainImageBytes().length == 0) {
            errors.rejectValue("mainImageBytes", "mainImageBytes.empty", "Main image cannot be empty.");
        }
    }
}
