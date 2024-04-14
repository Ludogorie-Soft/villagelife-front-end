function updateRequiredConsents() {
    const screenWidth = window.innerWidth;
    const consentSections = document.querySelectorAll('.lg-consents');

    consentSections.forEach(section => {
        const isVisible = window.getComputedStyle(section).display !== 'none';
        const consentInputs = section.querySelectorAll('input[type="radio"]');

        consentInputs.forEach(input => {
            input.required = isVisible;
        });
    });
}

window.addEventListener('resize', updateRequiredConsents);
updateRequiredConsents();