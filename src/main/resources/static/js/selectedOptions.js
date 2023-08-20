
    var selectedOptions = [];


    var selectedObjects = document.querySelectorAll('input[name="objectAroundVillageDTOS"]:checked');
    selectedObjects.forEach(function(object) {
        selectedOptions.push(object.value);
    });


    var selectedLivingConditions = document.querySelectorAll('input[name="livingConditionDTOS"]:checked');
    selectedLivingConditions.forEach(function(condition) {
        selectedOptions.push(condition.value);
    });


    var selectedChildren = document.querySelector('input[name="children"]:checked');
    if (selectedChildren) {
        selectedOptions.push(selectedChildren.value);
    }





    localStorage.setItem('selectedOptions', JSON.stringify(selectedOptions));






