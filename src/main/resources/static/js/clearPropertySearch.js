document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("clearSearchButton").addEventListener("click", function () {
        showClearWarningModal();
    });

    function showClearWarningModal() {
        const warningModal = new bootstrap.Modal(document.getElementById("clearWarningModal"));
        warningModal.show();

        document.getElementById("confirmClearSearch").onclick = function () {
            clearSearchData();
            warningModal.hide();
        };

        document.getElementById("cancelClearSearch").onclick = function () {
            warningModal.hide();
        };
    }

    function clearSearchData() {
        document.getElementById("regionName").value = "";
        document.getElementById("search-input").value = "";

        document.querySelectorAll("#advancedSearchModal input[type='checkbox']").forEach(input => input.checked = false);

        document.querySelectorAll("#advancedSearchModal input[type='radio']").forEach(input => input.checked = false);

        ["minBuiltUpArea", "maxBuiltUpArea", "minYardArea", "maxYardArea",
            "minRoomsCount", "maxRoomsCount", "minBathroomsCount", "maxBathroomsCount",
            "minPrice", "maxPrice", "minRentPrice", "maxRentPrice"
        ].forEach(id => {
            const element = document.getElementById(id);
            if (element) element.value = "";
        });

        document.querySelectorAll(".noUi-target").forEach(slider => {
            slider.noUiSlider.set([slider.noUiSlider.options.range.min, slider.noUiSlider.options.range.max]);
        });

        document.querySelectorAll("#advancedSearchModal select").forEach(select => {
            select.selectedIndex = 0;
        });
    }
});