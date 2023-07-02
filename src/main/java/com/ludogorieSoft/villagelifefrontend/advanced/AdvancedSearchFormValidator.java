package com.ludogoriesoft.villagelifefrontend.advanced;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class AdvancedSearchFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AdvancedSearchForm.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        AdvancedSearchForm formResult = (AdvancedSearchForm) target;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "children", "field.invalidValue");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "livingConditionDTOS", "field.required");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "objectAroundVillageDTOS", "field.required");

        String selectedChildrenCountResult = formResult.getChildren();
        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();

        if(selectedChildrenCountResult == null && selectedLivingConditions == null && selectedObjects == null){
//            errors.rejectValue("children", "field.invalidValue");
//            errors.rejectValue("livingConditionDTOS", "field.invalidLength", "Невалидна дължина на списъка");
//            errors.rejectValue("objectAroundVillageDTOS", "field.invalidLength", "Невалидна дължина на списъка");
            errors.reject("form.empty", "Формата е празна");

        }


//
//        String selectedChildrenCountResult = formResult.getChildren();
//        if (selectedChildrenCountResult != null) {
//            if (!Children.isValidValue(selectedChildrenCountResult)) {
//                errors.rejectValue("children", "field.invalidValue");
//            }
//        }
//
//
//        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
//
//        if (selectedLivingConditions == null || selectedLivingConditions.size() > 3) {
//            errors.rejectValue("livingConditionDTOS", "field.invalidLength", "Невалидна дължина на списъка");
//        }
//
//
//        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();
//
//        if (selectedObjects == null || selectedObjects.size() > 5) {
//            errors.rejectValue("objectAroundVillageDTOS", "field.invalidLength", "Невалидна дължина на списъка");
//        }


    }








}

