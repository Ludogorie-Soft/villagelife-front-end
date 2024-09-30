document.addEventListener("DOMContentLoaded", function () {
    const prevButton = document.getElementById("prevButton");
    const nextButton = document.getElementById("nextButton");
    const propertyItems = document.querySelectorAll(".property");
    const visibleItems = 3; // Брой видими имоти едновременно
    const itemCount = propertyItems.length;
    let currentIndex = 0;

    function showProperties(index) {
        if (index < 0) {
            index = 0;
        } else if (index > itemCount - visibleItems) {
            index = itemCount - visibleItems;
        }

        // Скриване на всички имоти
        propertyItems.forEach((item, i) => {
            if (i >= index && i < index + visibleItems) {
                item.style.display = "block"; // Показване на видимите имоти
            } else {
                item.style.display = "none"; // Скриване на останалите
            }
        });

        currentIndex = index;
    }

    function showNextProperty() {
        showProperties(currentIndex + 1);
    }

    function showPrevProperty() {
        showProperties(currentIndex - 1);
    }

    nextButton.addEventListener("click", showNextProperty);
    prevButton.addEventListener("click", showPrevProperty);

    // Показваме първоначалните 3 имота
    showProperties(currentIndex);
});
