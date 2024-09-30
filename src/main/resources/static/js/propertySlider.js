document.addEventListener("DOMContentLoaded", function () {
    const prevButton = document.getElementById("prevButton");
    const nextButton = document.getElementById("nextButton");
    const propertyGrid = document.querySelector(".property-grid");
    const propertyItems = document.querySelectorAll(".property");
    const visibleItems = 3; // Number of visible properties at once
    const totalItems = propertyItems.length;
    let currentIndex = 0;

    // Calculate total width of each property, including margin
    const propertyItemWidth = propertyItems[0].offsetWidth +
                              parseInt(window.getComputedStyle(propertyItems[0]).marginLeft) +
                              parseInt(window.getComputedStyle(propertyItems[0]).marginRight);

    // Function to update the transform position of the grid for sliding
    function updateGridPosition(index) {
        const maxIndex = totalItems - visibleItems; // Ensure we don't go beyond the last set of items
        if (index < 0) {
            index = 0; // Prevent going to negative indices
        } else if (index > maxIndex) {
            index = maxIndex; // Prevent going beyond the last set of items
        }

        const offset = -(index * propertyItemWidth); // Calculate the offset for sliding
        propertyGrid.style.transform = `translateX(${offset}px)`;
        currentIndex = index;
    }

    function showNextProperty() {
        updateGridPosition(currentIndex + 1);
    }

    function showPrevProperty() {
        updateGridPosition(currentIndex - 1);
    }

    nextButton.addEventListener("click", showNextProperty);
    prevButton.addEventListener("click", showPrevProperty);

    // Set the initial position
    updateGridPosition(currentIndex);
});
