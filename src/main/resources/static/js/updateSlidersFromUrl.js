document.addEventListener("DOMContentLoaded", function () {
        const queryString = window.location.search;
        const params = new URLSearchParams(queryString);

        function updateSliderFromParams(sliderId, minInputId, maxInputId, paramMinKey, paramMaxKey, defaultMin, defaultMax) {
            const slider = document.getElementById(sliderId);

            if (!slider || !slider.noUiSlider) return;

            const paramMin = parseInt(params.get(paramMinKey)) || defaultMin;
            const paramMax = parseInt(params.get(paramMaxKey)) || defaultMax;

            slider.noUiSlider.set([paramMin, paramMax]);

            const minInput = document.getElementById(minInputId);
            const maxInput = document.getElementById(maxInputId);
            if (minInput) minInput.value = paramMin !== defaultMin ? paramMin : '';
            if (maxInput) maxInput.value = paramMax !== defaultMax ? paramMax : '';
        }

        updateSliderFromParams("builtUpAreaSlider", "minBuiltUpArea", "maxBuiltUpArea", "minBuiltUpArea", "maxBuiltUpArea", 0, 1001);
        updateSliderFromParams("yardAreaSlider", "minYardArea", "maxYardArea", "minYardArea", "maxYardArea", 0, 5001);
        updateSliderFromParams("roomsSlider", "minRoomsCount", "maxRoomsCount", "minRoomsCount", "maxRoomsCount", 0, 21);
        updateSliderFromParams("bathroomsSlider", "minBathroomsCount", "maxBathroomsCount", "minBathroomsCount", "maxBathroomsCount", 0, 21);
        updateSliderFromParams("priceSlider", "minPrice", "maxPrice", "minPrice", "maxPrice", 0, 500001);
        updateSliderFromParams("priceRentSlider", "minRentPrice", "maxRentPrice", "minPrice", "maxPrice", 0, 5001);
        updateSliderFromParams("constructionYearSlider", "minConstructionYear", "maxConstructionYear", "minConstructionYear", "maxConstructionYear", 1900, (new Date().getFullYear() + 1));
    });