function validateGroundCategory() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"][name="groundCategoryIds"]');
    let checkedCount = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCount++;
        }
    });

    const errorElement = document.getElementById('ground-category-checkbox-error');
    if (checkedCount === 0) {
        if (errorElement) {
            errorElement.style.display = 'block';
        }

        const section = document.getElementById('groundCategory');
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
