    document.addEventListener("DOMContentLoaded", function () {
        var clearButton = document.getElementById("clearButton");

        clearButton.addEventListener("mouseover", function () {
            clearButton.style.color = "#000000"; // Черен цвят на текста
            clearButton.style.backgroundColor = "#9ac76d"; // Нов фонов цвят
        });

        clearButton.addEventListener("mouseout", function () {
            clearButton.style.color = ""; // Възстановява оригиналния цвят на текста
            clearButton.style.backgroundColor = ""; // Възстановява оригиналния фонов цвят
        });
    });