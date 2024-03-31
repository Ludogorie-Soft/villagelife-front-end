$(document).addEventListener("DOMContentLoaded", function() {
    var dropdownItems = document.querySelectorAll("#locales .dropdown-item");

    dropdownItems.forEach(function(item) {
        item.addEventListener("click", function(event) {
            event.preventDefault();
            var langCode = item.getAttribute("data-lang");
            changeLanguage(langCode);
        });
    });

    function changeLanguage(langCode) {
        var currentUrl = window.location.href;
        var newUrl = updateQueryStringParameter(currentUrl, "lang", langCode);
        window.location.replace(newUrl);
    }

    function updateQueryStringParameter(uri, key, value) {
        var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
        var separator = uri.indexOf("?") !== -1 ? "&" : "?";
        if (uri.match(re)) {
            return uri.replace(re, "$1" + key + "=" + value + "$2");
        } else {
            return uri + separator + key + "=" + value;
        }
    }
});