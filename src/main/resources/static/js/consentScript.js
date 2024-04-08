//const formFileMultiple = document.getElementById('formFileMultiple');
//const consentAndInfoContainer = document.getElementById('consentAndInfoContainer');
//const fullNameInput = document.getElementById('fullName');
//const emailInput = document.getElementById('email');
//const consentCheckbox = document.getElementById('consent');
//
//formFileMultiple.addEventListener('change', function() {
//    if (this.files.length > 0) {
//        consentAndInfoContainer.style.display = 'block';
//        fullNameInput.setAttribute('required', 'required');
//        emailInput.setAttribute('required', 'required');
//        consentCheckbox.setAttribute('required', 'required');
//
//        fullNameInput.setAttribute('minlength', '7');
//        emailInput.setAttribute('minlength', '10');
//    } else {
//        consentAndInfoContainer.style.display = 'none';
//        fullNameInput.removeAttribute('required');
//        emailInput.removeAttribute('required');
//        consentCheckbox.removeAttribute('required');
//
//        fullNameInput.removeAttribute('minlength');
//        emailInput.removeAttribute('minlength');
//    }
//});
const formFileMultiple = document.getElementById('formFileMultiple');
const consentAndInfoContainer = document.getElementById('consentAndInfoContainer');
const fullNameInput = document.getElementById('fullName');
const emailInput = document.getElementById('email');
const consentCheckbox = document.getElementById('cbx-43');
const nameError = document.getElementById('name-error');
const emailError = document.getElementById('email-error');
const consentError = document.getElementById('consent-error');

function validateConsentForm() {
    let valid = true;

    // Validation for name
    if (!fullNameInput.value.trim()) {
        nameError.style.display = 'block'; // Display the error message
        valid = false;
    } else {
        nameError.style.display = 'none'; // Hide the error message if the field is filled
    }

    // Validation for email
    if (!emailInput.value.trim() || !isValidEmail(emailInput.value.trim())) {
        emailError.style.display = 'block'; // Display the error message
        valid = false;
    } else {
        emailError.style.display = 'none'; // Hide the error message if the field is filled and email is valid
    }

    // Validation for consent
    if (!consentCheckbox.checked) {
        consentError.style.display = 'block'; // Display the error message
        valid = false;
    } else {
        consentError.style.display = 'none'; // Hide the error message if consent is selected
    }

    return valid;
}

// Function to check if email is valid
function isValidEmail(email) {
    // Regular expression for validating email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

    formFileMultiple.addEventListener('change', function() {
        if (this.files.length > 0) {
            consentAndInfoContainer.style.display = 'block';
            fullNameInput.setAttribute('required', 'required');
            emailInput.setAttribute('required', 'required');
            consentCheckbox.setAttribute('required', 'required');

            fullNameInput.setAttribute('minlength', '7');
            emailInput.setAttribute('minlength', '10');
        } else {
            consentAndInfoContainer.style.display = 'none';
            fullNameInput.removeAttribute('required');
            emailInput.removeAttribute('required');
            consentCheckbox.removeAttribute('required');

            fullNameInput.removeAttribute('minlength');
            emailInput.removeAttribute('minlength');
        }
    });
