document.addEventListener("DOMContentLoaded", function () {
    const prevButton = document.getElementById("prevButton");
    const nextButton = document.getElementById("nextButton");
    const propertyGrid = document.querySelector(".property-grid");
    const propertyItems = document.querySelectorAll(".property");
    const totalItems = propertyItems.length;
    let currentIndex = 0;

    function getVisibleItems() {
        const screenWidth = window.innerWidth;
        if (screenWidth >= 1200) {
            return 3;
        } else if (screenWidth >= 768) {
            return 2;
        } else {
            return 1;
        }
    }

    let visibleItems = getVisibleItems();

    function calculatePropertyWidth() {
        return propertyItems[0].offsetWidth +
            parseInt(window.getComputedStyle(propertyItems[0]).marginLeft) +
            parseInt(window.getComputedStyle(propertyItems[0]).marginRight);
    }

    function updateGridPosition(index) {
        visibleItems = getVisibleItems();
        const maxIndex = totalItems - visibleItems;
        const propertyItemWidth = calculatePropertyWidth();

        if (totalItems <= visibleItems) {
            propertyGrid.style.transform = 'translateX(0)';
            prevButton.style.display = 'none';
            nextButton.style.display = 'none';
        } else {
            prevButton.style.display = 'inline-block';
            nextButton.style.display = 'inline-block';

            if (index < 0) {
                index = 0;
            } else if (index > maxIndex) {
                index = maxIndex;
            }

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

    updateGridPosition(currentIndex);

    window.addEventListener("resize", function () {
        updateGridPosition(currentIndex);
    });
});
