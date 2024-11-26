document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('#advancedSearchModal form').addEventListener('submit', function(event) {
        event.preventDefault();

        let form = event.target;
        let formData = new FormData(form);
        let searchParams = new URLSearchParams();

        formData.forEach((value, key) => {
            if (value.trim() !== "") { // Only add the parameter if its value is not an empty string
                searchParams.append(key, value);
            }
        });

        let sortValue = form.querySelector('[name="sort"]').value;
        if (sortValue) {
            searchParams.set('sort', sortValue);
        }

        let actionURL = form.action;
        let newURL = actionURL + '?' + searchParams.toString();

        window.location.href = newURL;
    });
});