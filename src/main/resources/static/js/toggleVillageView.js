    function toggleVillageView() {
        var villageContainer = document.getElementById("village-container");
        var villageToggle = document.querySelector(".village-toggle a i");

        if (villageContainer.classList.contains("village-container-grid")) {
            villageContainer.classList.remove("village-container-grid");
            villageContainer.classList.add("village-container-list");
            villageToggle.classList.remove("fa-th-list");
            villageToggle.classList.add("fa-th");
        } else {
            villageContainer.classList.remove("village-container-list");
            villageContainer.classList.add("village-container-grid");
            villageToggle.classList.remove("fa-th");
            villageToggle.classList.add("fa-th-list");
        }
    }