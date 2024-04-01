package com.ludogorieSoft.villagelifefrontend.advanced;

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

        String selectedChildrenCountResult = formResult.getChildren();
        List<String> selectedLivingConditions = formResult.getLivingConditionDTOS();
        List<String> selectedObjects = formResult.getObjectAroundVillageDTOS();

        if(selectedChildrenCountResult == null && selectedLivingConditions == null && selectedObjects == null){
            errors.reject("form.empty", "Формата е празна");
        }
    }
}

