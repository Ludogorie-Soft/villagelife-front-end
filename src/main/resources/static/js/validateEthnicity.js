function validateEthnicity() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"][name="ethnicityDTOIds"]');
    let checkedCount = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCount++;
        }
    });

    if (checkedCount === 0) {
        document.getElementById('ethnicity-checkbox-error').style.display = 'block';

        const section = document.getElementById('ethnicity');
        if (section) {
            section.scrollIntoView({ behavior: 'smooth' });
        }

        return false;
    }

    return true;
}