const formFileMultiple = document.getElementById('formFileMultiple');
const consentAndInfoContainer = document.getElementById('consentAndInfoContainer');
const fullNameInput = document.getElementById('fullName');
const emailInput = document.getElementById('email');
const consentCheckbox = document.getElementById('consent');

formFileMultiple.addEventListener('change', function() {
    if (this.files.length > 0) {
        consentAndInfoContainer.style.display = 'block';
        fullNameInput.setAttribute('required', 'required');
        emailInput.setAttribute('required', 'required');
        consentCheckbox.setAttribute('required', 'required');
    } else {
        consentAndInfoContainer.style.display = 'none';
        fullNameInput.removeAttribute('required');
        emailInput.removeAttribute('required');
        consentCheckbox.removeAttribute('required');
    }
});
;