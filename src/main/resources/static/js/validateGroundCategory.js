function validateGroundCategory() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"][name="groundCategoryIds"]');
    let checkedCount = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCount++;
        }
    });

    if (checkedCount === 0) {
        document.getElementById('ground-category-checkbox-error').style.display = 'block';

        const section = document.getElementById('groundCategory');
        if (section) {
            section.scrollIntoView({ behavior: 'smooth' });
        }

        return false;
    }

    return true;
}