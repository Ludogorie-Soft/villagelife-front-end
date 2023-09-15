    document.addEventListener("DOMContentLoaded", function () {
        const prevButton = document.getElementById("prevButton");
        const nextButton = document.getElementById("nextButton");
        const villageGrid = document.querySelector(".village-grid");
        const villageItems = document.querySelectorAll(".village");
        const itemWidth = villageItems[0].offsetWidth;
        const itemCount = villageItems.length;
        let currentIndex = 0;

        function showVillages(index) {
            if (index < 0) {
                index = 0;
            } else if (index > itemCount - 3) {
                index = itemCount - 3;
            }
            const translateX = -index * (itemWidth) + "px" ;
            villageGrid.style.transform = `translateX(${translateX})`;
            currentIndex = index;
        }

        function showNextVillage() {
            showVillages(currentIndex + 1);
        }

        function showPrevVillage() {
            showVillages(currentIndex - 1);
        }

        nextButton.addEventListener("click", showNextVillage);
        prevButton.addEventListener("click", showPrevVillage);
    });