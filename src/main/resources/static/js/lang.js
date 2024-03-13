//$(document).ready(function() {
//    $(".option").click(function () {
//        var selectedOption = $(this).data('value');
//        if (selectedOption !== '') {
//            var currentUrl = window.location.href;
//            currentUrl = currentUrl.replace(/(\?|\&)lang=[^&]+/, '');
//            currentUrl += (currentUrl.includes('?') ? '&' : '?') + 'lang=' + selectedOption;
//            window.location.replace(currentUrl);
//        }
//    });
//});
//
//document.querySelector('.selected-option-container').addEventListener('click', function() {
//  const optionsContainer = document.querySelector('.options-container');
//  optionsContainer.style.display = optionsContainer.style.display === 'none' ? 'block' : 'none';
//});
//
//document.querySelectorAll('.option').forEach(option => {
//  option.addEventListener('click', function() {
//    document.querySelector('.selected-option').textContent = this.textContent;
//    document.querySelector('.options-container').style.display = 'none';
//  });
//});
//
//document.addEventListener("click", function(event) {
//    const customSelect = document.querySelector(".custom-select");
//    const optionsContainer = document.querySelector(".options-container");
//
//    if (!customSelect.contains(event.target)) {
//        optionsContainer.style.display = "none";
//    }
//});

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