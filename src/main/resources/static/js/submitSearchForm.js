    function submitSearchForm() {
        // Получаване на избраните опции
        var selectedObjects = Array.from(document.querySelectorAll('input[name="objectAroundVillageDTOS"]:checked')).map(function(input) {
            return input.value;
        });
        var selectedLivingConditions = Array.from(document.querySelectorAll('input[name="livingConditionDTOS"]:checked')).map(function(input) {
            return input.value;
        });
        var selectedChildrenCount = document.querySelector('select[name="children"]').value;

        // Задаване на стойностите в скритите полета
        document.querySelector('input[name="selectedObjects"]').value = JSON.stringify(selectedObjects);
        document.querySelector('input[name="selectedLivingConditions"]').value = JSON.stringify(selectedLivingConditions);
        document.querySelector('input[name="selectedChildrenCount"]').value = selectedChildrenCount;

        // Извикване на метода searchVillages
        searchVillages();
    }

    function searchVillages() {
        // Извикване на метода searchVillages или изпълнение на необходимата логика за търсенето
        // Например, изпращане на формата или извикване на API заявка

        // Пример: изпращане на формата
        document.getElementById("searchForm").submit();
    }

