    function submitSearchForm() {

        var selectedObjects = Array.from(document.querySelectorAll('input[name="objectAroundVillageDTOS"]:checked')).map(function(input) {
            return input.value;
        });
        var selectedLivingConditions = Array.from(document.querySelectorAll('input[name="livingConditionDTOS"]:checked')).map(function(input) {
            return input.value;
        });
        var selectedChildrenCount = document.querySelector('select[name="children"]').value;


        document.querySelector('input[name="selectedObjects"]').value = JSON.stringify(selectedObjects);
        document.querySelector('input[name="selectedLivingConditions"]').value = JSON.stringify(selectedLivingConditions);
        document.querySelector('input[name="selectedChildrenCount"]').value = selectedChildrenCount;


        searchVillages();
    }

    function searchVillages() {

        document.getElementById("searchForm").submit();
    }

