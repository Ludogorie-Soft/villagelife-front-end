    // Получаване на избраните опции
    var selectedOptions = [];

    // Получаване на избраните обекти в близост до селото
    var selectedObjects = document.querySelectorAll('input[name="objectAroundVillageDTOS"]:checked');
    selectedObjects.forEach(function(object) {
        selectedOptions.push(object.value);
    });

    // Получаване на избраните условия на живот
    var selectedLivingConditions = document.querySelectorAll('input[name="livingConditionDTOS"]:checked');
    selectedLivingConditions.forEach(function(condition) {
        selectedOptions.push(condition.value);
    });

     // Получаване на избраните деца
    var selectedChildren = document.querySelector('input[name="children"]:checked');
    if (selectedChildren) {
        selectedOptions.push(selectedChildren.value);
    }




    // Запазване на избраните опции в локалното съхранение
    localStorage.setItem('selectedOptions', JSON.stringify(selectedOptions));






