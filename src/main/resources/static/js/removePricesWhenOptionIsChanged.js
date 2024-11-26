document.addEventListener("DOMContentLoaded", function () {
    const rentOption = document.getElementById("rentOption");
    const saleOption = document.getElementById("saleOption");

    function clearPriceParams() {
        // Clear inputs for price sliders
        const minPriceInput = document.getElementById("minPrice");
        const maxPriceInput = document.getElementById("maxPrice");
        const minRentPriceInput = document.getElementById("minRentPrice");
        const maxRentPriceInput = document.getElementById("maxRentPrice");

        if (minPriceInput) minPriceInput.value = "";
        if (maxPriceInput) maxPriceInput.value = "";
        if (minRentPriceInput) minRentPriceInput.value = "";
        if (maxRentPriceInput) maxRentPriceInput.value = "";

        // Reset sliders to their min/max positions
        const priceSlider = document.getElementById("priceSlider");
        const priceRentSlider = document.getElementById("priceRentSlider");

        if (priceSlider?.noUiSlider) {
            priceSlider.noUiSlider.set([priceSlider.noUiSlider.options.range.min, priceSlider.noUiSlider.options.range.max]);
        }
        if (priceRentSlider?.noUiSlider) {
            priceRentSlider.noUiSlider.set([priceRentSlider.noUiSlider.options.range.min, priceRentSlider.noUiSlider.options.range.max]);
        }

        // Remove price parameters from the URL
        const url = new URL(window.location);
        url.searchParams.delete("minPrice");
        url.searchParams.delete("maxPrice");
        url.searchParams.delete("minRentPrice");
        url.searchParams.delete("maxRentPrice");
        window.history.replaceState({}, '', url); // Update the URL without reloading the page
    }

    rentOption.addEventListener("change", clearPriceParams);
    saleOption.addEventListener("change", clearPriceParams);
});