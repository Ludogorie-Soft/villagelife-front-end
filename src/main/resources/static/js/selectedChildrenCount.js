    function showSelectedChildrenCount(selectElement) {
    console.log("Function called");

    var selectedChildrenCount = selectElement.value;
    var selectedChildrenCountResult = document.getElementById("selectedChildrenCountResult");
    selectedChildrenCountResult.textContent = "Choice: " + selectedChildrenCount;

    var selectedOption = selectElement.options[selectElement.selectedIndex];
    selectedChildrenCountResult.textContent = "Selected option: " + selectedOption.text;

    console.log("Function finished");
    }





