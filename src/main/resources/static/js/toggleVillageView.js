    function toggleVillageView() {
    var villageContainer = document.getElementById("village-container");
    var villageToggle = document.querySelector(".village-toggle a i");

    villageContainer.classList.toggle("village-container-list");

    var imageContainers = villageContainer.querySelectorAll(".image-container");
    var cardBodies = villageContainer.querySelectorAll(".card-body");

    imageContainers.forEach(function (container) {
    container.classList.toggle("col-6");
    container.classList.toggle("col-md-12");
    });

    cardBodies.forEach(function (body) {
    body.classList.toggle("col-6");
    body.classList.toggle("col-md-12");
    });

    villageToggle.classList.toggle("fa-th");
    villageToggle.classList.toggle("fa-th-list");
    }