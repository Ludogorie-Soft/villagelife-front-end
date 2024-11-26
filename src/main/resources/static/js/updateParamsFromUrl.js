document.addEventListener("DOMContentLoaded", function () {
        const searchBarForm = document.querySelector(".search-bar-row form");
        const modalForm = document.querySelector("#advancedSearchModal form");

        /**
         * Populate form fields from query parameters
         */
        function populateFieldsFromQuery(queryString, form) {
            const params = new URLSearchParams(queryString);
            params.forEach((value, key) => {
                const fields = form.querySelectorAll(`[name="${key}"]`);
                fields.forEach(field => {
                    if (field.type === "checkbox" || field.type === "radio") {
                        field.checked = Array.isArray(value)
                            ? value.includes(field.value)
                            : field.value === value;
                    } else if (field.tagName === "SELECT") {
                        field.value = value;
                    } else {
                        field.value = value;
                    }
                });

                if (fields.length > 1 && fields[0].type === "checkbox") {
                    const values = params.getAll(key);
                    fields.forEach(field => {
                        field.checked = values.includes(field.value);
                    });
                }

                if (key.startsWith("min") || key.startsWith("max")) {
                    const sliderField = form.querySelector(`#${key}`);
                    if (sliderField) {
                        sliderField.value = value;
                        const associatedRuler = document.querySelector(`#${key}Ruler`);
                        if (associatedRuler) {
                            associatedRuler.innerHTML = value;
                        }

                        const slider = document.querySelector(`#${key}Slider`);
                        if (slider && slider.noUiSlider) {
                            const sliderValues = [
                                params.get("min" + key.slice(3)),
                                params.get("max" + key.slice(3)),
                            ];
                            slider.noUiSlider.set(sliderValues);
                        }
                    }
                }
            });
        }

        /**
         * Merge data from both forms
         */
        function mergeFormData(form1, form2) {
            const formData = new FormData(form1);
            for (let [key, value] of new FormData(form2)) {
                if (!formData.has(key)) {
                    formData.append(key, value);
                } else {
                    formData.delete(key);
                    const form1Values = new FormData(form1).getAll(key);
                    const form2Values = new FormData(form2).getAll(key);
                    const combinedValues = [...new Set([...form1Values, ...form2Values])];
                    combinedValues.forEach(v => formData.append(key, v));
                }
            }
            return new URLSearchParams(formData);
        }

        const queryString = window.location.search;
        if (queryString) {
            populateFieldsFromQuery(queryString, searchBarForm);
            populateFieldsFromQuery(queryString, modalForm);
        }

        searchBarForm.addEventListener("submit", function (e) {
            e.preventDefault();
            const queryParams = mergeFormData(searchBarForm, modalForm);
            const actionUrl = searchBarForm.getAttribute("action");
            window.location.href = `${actionUrl}?${queryParams.toString()}`;
        });

        modalForm.addEventListener("submit", function (e) {
            e.preventDefault();
            const queryParams = mergeFormData(searchBarForm, modalForm);
            const actionUrl = modalForm.getAttribute("action");
            window.location.href = `${actionUrl}?${queryParams.toString()}`;
        });
    });