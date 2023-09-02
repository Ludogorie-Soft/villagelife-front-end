package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.dtos.AddVillageFormResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class AddVillageFormValidator  implements Validator {
    private static final String FIELD_REQUIRED = "field.required";
    @Override
    public boolean supports(Class<?> clazz) {
        return AddVillageFormResult.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        AddVillageFormResult addVillageFormResult = (AddVillageFormResult) target;

        if (addVillageFormResult.getVillageDTO().getName().equals("")) {
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "Въведете име на селото");
        }
        if (addVillageFormResult.getEthnicityDTOIds() == null) {
            errors.rejectValue("ethnicityDTOIds", FIELD_REQUIRED, "Изберете поне една малцинствена група");
        }
        if (addVillageFormResult.getGroundCategoryIds() == null) {
            errors.rejectValue("groundCategoryIds", FIELD_REQUIRED, "Изберете поне една категория на замята");
        }
        if (addVillageFormResult.getPopulationDTO().getPopulationCount() < 1) {
            errors.rejectValue("populationDTO.populationCount", "field.minlength", "Броят на населението трябва да е по-голямо или равно на 1");
        }
        if (addVillageFormResult.getPopulationDTO().getResidents() == null) {
            errors.rejectValue("populationDTO.residents", FIELD_REQUIRED, "Изберете каква част от жителите на селото са хора до 50 години");
        }
        if (addVillageFormResult.getPopulationDTO().getChildren() == null) {
            errors.rejectValue("populationDTO.children", FIELD_REQUIRED, "Изберете какъв е броят на децата (до 14г.)");
        }
        if (addVillageFormResult.getPopulationDTO().getForeigners() == null) {
            errors.rejectValue("populationDTO.foreigners", FIELD_REQUIRED, "Отговорете дали в селото живеят ли чужденци");
        }
    }
}
