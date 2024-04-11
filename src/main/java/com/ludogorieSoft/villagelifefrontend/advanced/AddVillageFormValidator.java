package com.ludogorieSoft.villagelifefrontend.advanced;

import com.ludogorieSoft.villagelifefrontend.config.RegionClient;
import com.ludogorieSoft.villagelifefrontend.config.ValidationUtilsClient;
import com.ludogorieSoft.villagelifefrontend.dtos.AddVillageFormResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class AddVillageFormValidator implements Validator {
    private static final String FIELD_REQUIRED = "field.required";

    private final ValidationUtilsClient validationUtilsClient;
    private final RegionClient regionClient;


    @Override
    public boolean supports(Class<?> clazz) {
        return AddVillageFormResult.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddVillageFormResult addVillageFormResult = (AddVillageFormResult) target;

        if (addVillageFormResult.getVillageDTO().getName().trim().equals("")) {
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "Enter village name!");
        }/* else if (Boolean.FALSE.equals(validationUtilsClient.usernameCheck(addVillageFormResult.getVillageDTO().getName()))) { //method already check for cyrillic and latin names
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "Must use letters only!");
        }*/

        if(Boolean.FALSE.equals(regionClient.existsRegionByName(addVillageFormResult.getVillageDTO().getRegion()))){
            errors.rejectValue("villageDTO.region", FIELD_REQUIRED, "You must choose a region!");
        }

        if (addVillageFormResult.getEthnicityDTOIds() == null) {
            errors.rejectValue("ethnicityDTOIds", FIELD_REQUIRED, "Choose at least one ethnicity group!");
        }
        if (addVillageFormResult.getGroundCategoryIds() == null) {
            errors.rejectValue("groundCategoryIds", FIELD_REQUIRED, "Choose at least one ground category");
        }
        if (addVillageFormResult.getPopulationDTO().getPopulationCount() < 1) {
            errors.rejectValue("populationDTO.populationCount", "field.minlength", "The population count must be greater than or equal to 1!");
        }
        if (addVillageFormResult.getPopulationDTO().getResidents() == null) {
            errors.rejectValue("populationDTO.residents", FIELD_REQUIRED, "Select what percentage of the village residents are people under 50 years old!");
        }
        if (addVillageFormResult.getPopulationDTO().getChildren() == null) {
            errors.rejectValue("populationDTO.children", FIELD_REQUIRED, "Select the number of children (up to 14 years old)!");
        }
        if (addVillageFormResult.getPopulationDTO().getForeigners() == null) {
            errors.rejectValue("populationDTO.foreigners", FIELD_REQUIRED, "Answer whether there are foreigners living in the village!");
        }
    }
}
