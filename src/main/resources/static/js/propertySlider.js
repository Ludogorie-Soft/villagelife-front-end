document.addEventListener("DOMContentLoaded", function () {
    const prevButton = document.getElementById("prevButton");
    const nextButton = document.getElementById("nextButton");
    const propertyGrid = document.querySelector(".property-grid");
    const propertyItems = document.querySelectorAll(".property");
    const totalItems = propertyItems.length;
    let currentIndex = 0;

    // Function to calculate the number of visible items based on screen width
    function getVisibleItems() {
        const screenWidth = window.innerWidth;
        if (screenWidth >= 1200) {
            return 3; // Large screens
        } else if (screenWidth >= 768) {
            return 2; // Medium screens
        } else {
            return 1; // Small screens
        }
    }

    // Get the current visible items based on screen size
    let visibleItems = getVisibleItems();

    // Calculate total width of each property, including margin
    function calculatePropertyWidth() {
        return propertyItems[0].offsetWidth +
            parseInt(window.getComputedStyle(propertyItems[0]).marginLeft) +
            parseInt(window.getComputedStyle(propertyItems[0]).marginRight);
    }

    // Function to update the transform position of the grid for sliding
    function updateGridPosition(index) {
        const maxIndex = totalItems - visibleItems; // Ensure we don't go beyond the last set of items
        if (index < 0) {
            index = 0; // Prevent going to negative indices
        } else if (index > maxIndex) {
            index = maxIndex; // Prevent going beyond the last set of items
        }

        const propertyItemWidth = calculatePropertyWidth(); // Recalculate in case of resize
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

    // Update layout on screen resize
    window.addEventListener("resize", function () {
        visibleItems = getVisibleItems(); // Recalculate visible items
        updateGridPosition(currentIndex); // Update the grid position for new layout
    });
});
