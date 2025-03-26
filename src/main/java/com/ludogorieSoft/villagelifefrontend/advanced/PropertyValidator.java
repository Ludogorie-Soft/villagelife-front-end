package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.config.VillageClient;
import com.ludogorieSoft.villagelifefrontend.dtos.InquiryDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.PropertyImageDTO;
import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

import static com.ludogorieSoft.villagelifefrontend.enums.PropertyTransferType.RENT;
import static com.ludogorieSoft.villagelifefrontend.enums.PropertyTransferType.SALE;

@Component
@AllArgsConstructor
public class PropertyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PropertyDTO.class.equals(clazz);
    }

    private static final String FIELD_REQUIRED = "field.required";
    private final ValidationUtilsClient validationUtilsClient;
    private VillageClient villageClient;

    @Override
    public void validate(Object target, Errors errors) {
        PropertyDTO propertyDTO = (PropertyDTO) target;
        if (propertyDTO.getVillageDTO().getName() == null || propertyDTO.getVillageDTO().getName().trim().isEmpty()) {
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "villageDTO.name.not.null");
        }
        if (propertyDTO.getVillageDTO().getRegion() == null || propertyDTO.getVillageDTO().getRegion().trim().isEmpty()) {
            errors.rejectValue("villageDTO.region", FIELD_REQUIRED, "villageDTO.region.not.null");
        }
        if(!Objects.equals(propertyDTO.getVillageDTO().getName(), "") && !Objects.equals(propertyDTO.getVillageDTO().getRegion(), "")){
            VillageDTO villageDTO = villageClient.findVillageByNameAndRegion(propertyDTO.getVillageDTO().getName() + ", " + propertyDTO.getVillageDTO().getRegion());
            if (villageDTO == null) {
                errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "villageDTO.can.not.be.found");
            }
        }
        if (propertyDTO.getPrice() == null) {
            if (propertyDTO.getPropertyTransferType() == SALE){
                errors.rejectValue("price", FIELD_REQUIRED, "price.not.null");
            }
            else if (propertyDTO.getPropertyTransferType() == RENT){
                errors.rejectValue("price", FIELD_REQUIRED, "monthly.rental.price.not.null");
            }
        } else if (propertyDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            if (propertyDTO.getPropertyTransferType() == SALE) {
                errors.rejectValue("price", "field.minlength", "price.not.less.than.zero");
            }
            else if (propertyDTO.getPropertyTransferType() == RENT){
                errors.rejectValue("price", "field.minlength", "monthly.rental.price.not.less.than.zero");
            }
        }
        if (propertyDTO.getBuildUpArea() == null) {
            errors.rejectValue("buildUpArea", FIELD_REQUIRED, "buildUpArea.not.null");
        } else if (propertyDTO.getBuildUpArea() < 0) {
            errors.rejectValue("buildUpArea", "field.minlength", "buildUpArea.not.less.than.zero");
        }
        if (propertyDTO.getYardArea() == null) {
            errors.rejectValue("yardArea", FIELD_REQUIRED, "yardArea.not.null");
        } else if (propertyDTO.getYardArea() < 0) {
            errors.rejectValue("yardArea", "field.minlength", "yardArea.not.less.than.zero");
        }
        if (propertyDTO.getRoomsCount() < 0) {
            errors.rejectValue("roomsCount", "field.minlength", "roomsCount.not.less.than.zero");
        }
        if (propertyDTO.getBathroomsCount() < 0) {
            errors.rejectValue("bathroomsCount", "field.minlength", "bathroomsCount.not.less.than.zero");
        }

        if (propertyDTO.getPhoneNumber() == null) {
            errors.rejectValue("phoneNumber", FIELD_REQUIRED, "phoneNumber.not.null");
        } else if (propertyDTO.getPhoneNumber().trim().length() < 10) {
            errors.rejectValue("phoneNumber", "field.minlength", "phoneNumber.not.less.than.ten");
        } else if (Boolean.FALSE.equals(validationUtilsClient.numberCheck(propertyDTO.getPhoneNumber()))) {
            errors.rejectValue("phoneNumber", "field.invalid", "phoneNumber.numbersonly");
        }
        if (propertyDTO.getPropertyType() == null) {
            errors.rejectValue("propertyType", FIELD_REQUIRED, "propertyType.not.null");
        }
        if (propertyDTO.getOwnershipType() == null) {
            errors.rejectValue("ownershipType", FIELD_REQUIRED, "ownershipType.not.null");
        }
        if (propertyDTO.getPropertyCondition() == null) {
            errors.rejectValue("propertyCondition", FIELD_REQUIRED, "propertyCondition.not.null");
        }
        if (propertyDTO.getDescription() == null || propertyDTO.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", FIELD_REQUIRED, "description.not.null");
        } else if (propertyDTO.getDescription().length() < 10) {
            errors.rejectValue("description", "field.minlength", "description.not.less.than.ten");
        } else if (propertyDTO.getDescription().length() > 500) {
            errors.rejectValue("description", "field.maxlength", "description.not.more.than.max.value");
        }

        if (propertyDTO.getAddress() == null || propertyDTO.getAddress().trim().isEmpty()) {
            errors.rejectValue("address", FIELD_REQUIRED, "address.not.null");
        }
        if (propertyDTO.getImages() == null || propertyDTO.getImages().isEmpty() || propertyDTO.getImages().get(0).getPropertyImageBytes() == null) {
            errors.rejectValue("images", FIELD_REQUIRED, "images.not.null");
        } else {

            for (int i = 0; i < propertyDTO.getImages().size(); i++) {
                PropertyImageDTO image = propertyDTO.getImages().get(i);
                if (image.getPropertyImageBytes() == null || image.getPropertyImageBytes().length == 0) {
                    errors.rejectValue("images[" + i + "].propertyImageBytes", FIELD_REQUIRED, "image.not.null");
                }
            }
        }
        if (propertyDTO.getMainImageBytes() == null) {
            errors.rejectValue("mainImageBytes", FIELD_REQUIRED, "mainImageBytes.not.null");
        } else if (propertyDTO.getMainImageBytes().length == 0) {
            errors.rejectValue("mainImageBytes", FIELD_REQUIRED, "mainImageBytes.not.empty");
        }
    }
}
