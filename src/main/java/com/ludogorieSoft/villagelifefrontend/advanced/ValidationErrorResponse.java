package com.ludogoriesoft.villagelifefrontend.advanced;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ValidationErrorResponse {
    private List<String> errors;

    public ValidationErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> objectErrors) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            errorMessages.add(objectError.getDefaultMessage());
        }
        this.errors = errorMessages;
    }


    private String message;


    public void setMessage(String message) {
        this.message = message;
    }

    private AdvancedSearchFormValidator formValidator;


    @PostMapping("/submit-form")
    public ResponseEntity<?> submitForm(@Valid @RequestBody AdvancedSearchForm formResult, BindingResult bindingResult) {
        // Валидацията
        formValidator.validate(formResult, bindingResult);

        // Проверка за грешки
        if (bindingResult.hasErrors()) {
            // Създаване на обект за грешка
            ValidationErrorResponse errorResponse = new ValidationErrorResponse();
            errorResponse.setMessage("Грешка при валидацията на формата!!!");
            errorResponse.setErrors(bindingResult.getAllErrors());

            // Връщане на грешката към клиента
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Ако няма грешки, продължаваме с обработката на формата

        return ResponseEntity.ok("Формата е изпратена успешно!");
    }

}
