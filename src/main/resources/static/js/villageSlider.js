    document.addEventListener("DOMContentLoaded", function () {
        const prevButton = document.getElementById("prevButton");
        const nextButton = document.getElementById("nextButton");
        const propertyGrid = document.querySelector(".property-grid");
        const propertyItems = document.querySelectorAll(".property");
        const itemWidth = propertyItems[0].offsetWidth;
        const itemCount = propertyItems.length;
        let currentIndex = 0;

        function showProperties(index) {
            if (index < 0) {
                index = 0;
            } else if (index > itemCount - 3) {
                index = itemCount - 3;
            }
            const translateX = -index * (itemWidth) + "px" ;
            propertyGrid.style.transform = `translateX(${translateX})`;
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
    });