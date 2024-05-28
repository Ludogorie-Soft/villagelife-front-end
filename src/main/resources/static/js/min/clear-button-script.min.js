    document.addEventListener("DOMContentLoaded", function () {
        var clearButton = document.getElementById("clearButton");

        clearButton.addEventListener("mouseover", function () {
            clearButton.style.color = "#000000";
            clearButton.style.backgroundColor = "#9ac76d";
        });

        clearButton.addEventListener("mouseout", function () {
            clearButton.style.color = "";
            clearButton.style.backgroundColor = "";
        });
    });