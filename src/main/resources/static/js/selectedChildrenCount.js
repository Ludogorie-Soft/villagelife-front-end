    function showSelectedChildrenCount(selectElement) {
    console.log("Function called"); // Проверка за извикване на функцията

    var selectedChildrenCount = selectElement.value;
    var selectedChildrenCountResult = document.getElementById("selectedChildrenCountResult");
    selectedChildrenCountResult.textContent = "Избор: " + selectedChildrenCount;

    var selectedOption = selectElement.options[selectElement.selectedIndex];
    selectedChildrenCountResult.textContent = "Избрана опция: " + selectedOption.text;

    console.log("Function finished"); // Проверка за завършване на функцията
    }





