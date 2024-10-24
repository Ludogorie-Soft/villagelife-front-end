function validateBusinessCardDTO() {
        var role = localStorage.getItem('activeTab').split('-')[0];
        if (role === 'agency') {
            const nameInput = document.getElementById('businessCardNameA');
            const emailInput = document.getElementById('businessCardEmailA');
            const phoneNumberInput = document.getElementById('businessCardPhoneNumberA');
            const addressInput = document.getElementById('businessCardAddressA');
            const numberOfEmployeesInput = document.getElementById('numberOfEmployeesA');
            let isValid = true;

        if (!nameInput || nameInput.value.trim() === '') {
            showError('businessCardNameErrorA', 'business.card.dto.validations.name.blank');
            isValid = false;
        } else {
            hideError('businessCardNameErrorA');
        }

        if (!emailInput || emailInput.value.trim() === '') {
            showError('businessCardEmailErrorA', 'Business email cannot be empty!!!');
            isValid = false;
        } else {
            hideError('businessCardEmailErrorA');
        }

        const phonePattern = /^\+?[0-9. ()-]{7,25}$/;

        if (!phoneNumberInput || phoneNumberInput.value.trim() === '') {
            showError('businessCardPhoneNumberErrorA', 'Business phone number cannot be empty!!!');
            isValid = false;
        } else if (!phonePattern.test(phoneNumberInput.value.trim())) {
            showError('businessCardPhoneNumberErrorA', 'Business phone number format is invalid!!!');
            isValid = false;
        } else {
            hideError('businessCardPhoneNumberErrorA');
        }

        if (!addressInput || addressInput.value.trim() === '') {
            showError('businessCardAddressErrorA', 'Business address cannot be empty!!!');
            isValid = false;
        } else {
            hideError('businessCardAddressErrorA');
        }

        if (!numberOfEmployeesInput || parseInt(numberOfEmployeesInput.value, 10) < 1) {
            showError('numberOfEmployeesErrorA', 'Number of employees cannot be less than 1!!!');
            isValid = false;
        } else {
            hideError('numberOfEmployeesErrorA');
        }

        return isValid;
        }
    if (role === 'builder') {
        const nameInput = document.getElementById('businessCardNameB');
        const emailInput = document.getElementById('businessCardEmailB');
        const phoneNumberInput = document.getElementById('businessCardPhoneNumberB');
        const addressInput = document.getElementById('businessCardAddressB');
        const numberOfEmployeesInput = document.getElementById('numberOfEmployeesB');
        let isValid = true;

        if (!nameInput || nameInput.value.trim() === '') {
            showError('businessCardNameErrorB', 'Business name cannot be empty!!!');
            isValid = false;
        } else {
            hideError('businessCardNameErrorB');
        }

        if (!emailInput || emailInput.value.trim() === '') {
            showError('businessCardEmailErrorB', 'Business email cannot be empty!!!');
            isValid = false;
        } else {
            hideError('businessCardEmailErrorB');
        }

        const phonePattern = /^\+?[0-9. ()-]{7,25}$/;

        if (!phoneNumberInput || phoneNumberInput.value.trim() === '') {
            showError('businessCardPhoneNumberErrorB', 'Business phone number cannot be empty!!!');
            isValid = false;
        } else if (!phonePattern.test(phoneNumberInput.value.trim())) {
            showError('businessCardPhoneNumberErrorB', 'Business phone number format is invalid!!!');
            isValid = false;
        } else {
            hideError('businessCardPhoneNumberErrorB');
        }

        if (!addressInput || addressInput.value.trim() === '') {
            showError('businessCardAddressErrorB', 'Business address cannot be empty!!!');
            isValid = false;
        } else {
            hideError('businessCardAddressErrorB');
        }

        if (!numberOfEmployeesInput || parseInt(numberOfEmployeesInput.value, 10) < 1) {
            showError('numberOfEmployeesErrorB', 'Number of employees cannot be less than 1!!!');
            isValid = false;
        } else {
            hideError('numberOfEmployeesErrorB');
        }

        return isValid;
        }
        if (role === 'investor') {
            const nameInput = document.getElementById('businessCardNameI');
            const emailInput = document.getElementById('businessCardEmailI');
            const phoneNumberInput = document.getElementById('businessCardPhoneNumberI');
            const addressInput = document.getElementById('businessCardAddressI');
            const numberOfEmployeesInput = document.getElementById('numberOfEmployeesI');
            let isValid = true;

            if (!nameInput || nameInput.value.trim() === '') {
                showError('businessCardNameErrorI', 'Business name cannot be empty!!!');
                isValid = false;
            } else {
                hideError('businessCardNameErrorI');
            }

            if (!emailInput || emailInput.value.trim() === '') {
                showError('businessCardEmailErrorI', 'Business email cannot be empty!!!');
                isValid = false;
            } else {
                hideError('businessCardEmailErrorI');
            }

            const phonePattern = /^\+?[0-9. ()-]{7,25}$/;

            if (!phoneNumberInput || phoneNumberInput.value.trim() === '') {
                showError('businessCardPhoneNumberErrorI', 'Business phone number cannot be empty!!!');
                isValid = false;
            } else if (!phonePattern.test(phoneNumberInput.value.trim())) {
                showError('businessCardPhoneNumberErrorI', 'Business phone number format is invalid!!!');
                isValid = false;
            } else {
                hideError('businessCardPhoneNumberErrorI');
            }

            if (!addressInput || addressInput.value.trim() === '') {
                showError('businessCardAddressErrorI', 'Business address cannot be empty!!!');
                isValid = false;
            } else {
                hideError('businessCardAddressErrorI');
            }

            if (!numberOfEmployeesInput || parseInt(numberOfEmployeesInput.value, 10) < 1) {
                showError('numberOfEmployeesErrorI', 'Number of employees cannot be less than 1!!!');
                isValid = false;
            } else {
                hideError('numberOfEmployeesErrorI');
            }

            return isValid;
            }
}

function showError(elementId, message) {
const errorElement = document.getElementById(elementId);
if (errorElement) {
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}
}

function hideError(elementId) {
const errorElement = document.getElementById(elementId);
if (errorElement) {
    errorElement.style.display = 'none';
}
}