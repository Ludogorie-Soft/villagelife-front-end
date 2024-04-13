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
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "error.enterVillageName");
        }/* else if (Boolean.FALSE.equals(validationUtilsClient.usernameCheck(addVillageFormResult.getVillageDTO().getName()))) { //method already check for cyrillic and latin names
            errors.rejectValue("villageDTO.name", FIELD_REQUIRED, "error.useLettersOnly");
        }*/

        if(Boolean.FALSE.equals(regionClient.existsRegionByName(addVillageFormResult.getVillageDTO().getRegion()))){
            errors.rejectValue("villageDTO.region", FIELD_REQUIRED, "error.chooseRegion");
        }

        if (addVillageFormResult.getEthnicityDTOIds() == null) {
            errors.rejectValue("ethnicityDTOIds", FIELD_REQUIRED, "error.chooseEthnicityGroup");
        }
        if (addVillageFormResult.getGroundCategoryIds() == null) {
            errors.rejectValue("groundCategoryIds", FIELD_REQUIRED, "error.chooseGroundCategory");
        }
        if (addVillageFormResult.getPopulationDTO().getPopulationCount() < 1) {
            errors.rejectValue("populationDTO.populationCount", "field.minlength", "error.populationCountMin");
        }
        if (addVillageFormResult.getPopulationDTO().getResidents() == null) {
            errors.rejectValue("populationDTO.residents", FIELD_REQUIRED, "error.selectPercentageUnder50");
        }
        if (addVillageFormResult.getPopulationDTO().getChildren() == null) {
            errors.rejectValue("populationDTO.children", FIELD_REQUIRED, "error.selectNumberOfChildren");
        }
        if (addVillageFormResult.getPopulationDTO().getForeigners() == null) {
            errors.rejectValue("populationDTO.foreigners", FIELD_REQUIRED, "error.answerForeignersLiving");
        }
    }
}