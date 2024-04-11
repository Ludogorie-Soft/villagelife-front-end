function validateEthnicity() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"][name="ethnicityDTOIds"]');
    let checkedCount = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCount++;
        }
    });

    const errorElement = document.getElementById('ethnicity-checkbox-error');
    if (checkedCount === 0) {
        if (errorElement) {
            errorElement.style.display = 'block';
        }

        const section = document.getElementById('ethnicity');
        if (section) {
            section.scrollIntoView({ behavior: 'smooth' });
        }

        return false;
    } else {
        if (errorElement) {
            errorElement.style.display = 'none';
        }
    }

    return true;
}


function validatePopulationForm() {
    var isForeignersValid = validatePopulation('foreigners', 'foreigners-error');
    var isResidentsValid = validatePopulation('residents', 'residents-error');
    var isChildrenValid = validatePopulation('children', 'children-error');

    if (!isForeignersValid || !isResidentsValid || !isChildrenValid) {
        return false;
    }
    return true;
}

function validatePopulation(populationType, errorId) {
    const checkboxes = document.querySelectorAll(`input[type="radio"][name="populationDTO.${populationType}"]`);
    let checkedCount = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCount++;
        }
    });

    const errorElement = document.getElementById(errorId);
    if (checkedCount === 0) {
        if (errorElement) {

            errorElement.style.display = 'block'; // Display error message
        }

        const section = document.getElementById(errorId);
        if (section) {
            section.scrollIntoView({ behavior: 'smooth' });
        }

        return false;
    } else {
        if (errorElement) {
            errorElement.style.display = 'none'; // Hide error message if validation succeeds
        }
    }

    return true;
}
