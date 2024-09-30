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

    // Function to update the grid position and visibility of buttons
    function updateGridPosition(index) {
        visibleItems = getVisibleItems(); // Recalculate visible items on each call
        const maxIndex = totalItems - visibleItems; // Max sliding index
        const propertyItemWidth = calculatePropertyWidth(); // Recalculate in case of resize

        // If there are fewer properties than visible items, reset the transform and hide buttons
        if (totalItems <= visibleItems) {
            // Align items from the left by setting transform to 0
            propertyGrid.style.transform = 'translateX(0)';
            // Hide the buttons since no sliding is needed
            prevButton.style.display = 'none';
            nextButton.style.display = 'none';
        } else {
            // Enable the buttons if sliding is possible
            prevButton.style.display = 'inline-block';
            nextButton.style.display = 'inline-block';

            // Ensure the index doesn't go below zero or beyond the max index
            if (index < 0) {
                index = 0;
            } else if (index > maxIndex) {
                index = maxIndex;
            }

            // Calculate the offset for sliding
            const offset = -(index * propertyItemWidth);
            propertyGrid.style.transform = `translateX(${offset}px)`;
            currentIndex = index;
        }
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
        updateGridPosition(currentIndex); // Update the grid position for new layout
    });
});
