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

        if (propertyDTO.getPrice() == null) {
            errors.rejectValue("price", FIELD_REQUIRED, "price.not.null");//The price must be greater than or equal to 0 and cannot be empty.
        }
        else if (propertyDTO.getPrice().compareTo(BigDecimal.ZERO) < 0){
            errors.rejectValue("price", "field.minlength", "price.not.less.than.zero");
        }

        if (propertyDTO.getBuildUpArea() == null) {
            errors.rejectValue("buildUpArea", FIELD_REQUIRED, "buildUpArea.not.null");//Build-up area must be greater than or equal to 0.
        }
        else if (propertyDTO.getBuildUpArea() < 0){
            errors.rejectValue("buildUpArea", "field.minlength", "buildUpArea.not.less.than.zero");
        }
        if (propertyDTO.getYardArea() == null) {
            errors.rejectValue("yardArea", FIELD_REQUIRED, "yardArea.not.null");//Yard area must be greater than or equal to 0.
        }
        else if (propertyDTO.getYardArea() < 0){
            errors.rejectValue("yardArea", "field.minlength", "yardArea.not.less.than.zero");
        }
        if (propertyDTO.getRoomsCount() < 0) {
            errors.rejectValue("roomsCount", "field.minlength", "roomsCount.not.less.than.zero");//The number of rooms must be greater than or equal to 0.
        }
        if (propertyDTO.getBathroomsCount() < 0) {
            errors.rejectValue("bathroomsCount", "field.minlength", "bathroomsCount.not.less.than.zero");//The number of bathrooms must be greater than or equal to 0.
        }

        if (propertyDTO.getPhoneNumber() == null) {
            errors.rejectValue("phoneNumber", FIELD_REQUIRED, "phoneNumber.not.null");//Phone number must be at least 10 digits long.
        }
        else if (propertyDTO.getPhoneNumber().trim().length() < 10){
            errors.rejectValue("phoneNumber", "field.minlength", "phoneNumber.not.less.than.ten");
        }
        else if (Boolean.FALSE.equals(validationUtilsClient.numberCheck(propertyDTO.getPhoneNumber()))) {
            errors.rejectValue("phoneNumber", "field.invalid", "phoneNumber.numbersonly");//Phone number must contain only digits.
        }
        if (propertyDTO.getPropertyType() == null) {
            errors.rejectValue("propertyType", FIELD_REQUIRED, "propertyType.not.null");//You must select a valid property type.
        }
        if (propertyDTO.getOwnershipType() == null) {
            errors.rejectValue("ownershipType", FIELD_REQUIRED, "ownershipType.not.null");//You must select a valid ownership type.
        }
        if (propertyDTO.getPropertyCondition() == null) {
            errors.rejectValue("propertyCondition", FIELD_REQUIRED, "propertyCondition.not.null");//You must select a valid property condition.
        }
        if (propertyDTO.getDescription() == null || propertyDTO.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", FIELD_REQUIRED, "description.not.null");//Description is required and cannot be blank.
        } else if (propertyDTO.getDescription().length() < 10 ) {
            errors.rejectValue("description", "field.minlength", "description.not.less.than.ten");//Description must be between 10 and 500 characters long.
        }
        else if (propertyDTO.getDescription().length() > 500){
            errors.rejectValue("description", "field.maxlength", "description.not.more.than.max.value");
        }

        if (propertyDTO.getAddress() == null || propertyDTO.getAddress().trim().isEmpty()) {
            errors.rejectValue("address", FIELD_REQUIRED, "address.not.null");//Address is required and cannot be blank.
        }
        if (propertyDTO.getVillageDTO().getName() == null || propertyDTO.getVillageDTO().getName().trim().isEmpty()) {
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "villageDTO.name.not.null");//Village name is required.
        }
        if (propertyDTO.getVillageDTO().getRegion() == null || propertyDTO.getVillageDTO().getRegion().trim().isEmpty()) {
            errors.rejectValue("villageDTO.region", FIELD_REQUIRED, "villageDTO.region.not.null");//Village region is required.
        }
        if (propertyDTO.getImages() == null || propertyDTO.getImages().isEmpty() || propertyDTO.getImages().get(0).getPropertyImageBytes() == null) {
            errors.rejectValue("images", FIELD_REQUIRED, "images.not.null");//At least one image is required.
        } else {
            // Ограничение за размер на всяко изображение (500 KB)
            long totalSizeInKB = 0;
            for (int i = 0; i < propertyDTO.getImages().size(); i++) {
                PropertyImageDTO image = propertyDTO.getImages().get(i);

                // Проверка дали изображението не е празно
                if (image.getPropertyImageBytes() == null || image.getPropertyImageBytes().length == 0) {
                    errors.rejectValue("images[" + i + "].propertyImageBytes", FIELD_REQUIRED, "image.not.null");//Image cannot be empty.
                } else {
                    // Проверка за размер на изображението (500 KB = 512000 bytes)
                    long imageSizeInKB = image.getPropertyImageBytes().length / 1024;
                    totalSizeInKB += imageSizeInKB;

                    if (imageSizeInKB > 500) {
                        errors.rejectValue("images[" + i + "].propertyImageBytes", "field.maxlength", "image.size.not.more.than.max.value");//Image size cannot exceed 500 KB.
                    }

                    // Лимитиране на общия размер на всички изображения (например 10 MB = 10240 KB)
                    if (totalSizeInKB > 10240) {
                        errors.rejectValue("images", "field.maxlength", "images.totalSize.not.more.than.max.value");//Total size of all images cannot exceed 10 MB.
                        break; // Спиране на проверката, ако лимитът е надхвърлен
                    }
                }
            }
        }
        if (propertyDTO.getMainImageBytes() == null) {
            errors.rejectValue("mainImageBytes", FIELD_REQUIRED, "mainImageBytes.not.null");//Main image is required.
        }
        else if(propertyDTO.getMainImageBytes().length == 0) {
            errors.rejectValue("mainImageBytes", FIELD_REQUIRED, "mainImageBytes.not.empty");//Main image cannot be empty.
        }
    }
}
