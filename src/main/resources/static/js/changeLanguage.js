    function changeLanguage() {
        var selectedLanguage = document.getElementById("language-select").value;

        // Send AJAX request to change the language
        $.ajax({
            type: "GET",
            url: "/locale?lang=" + selectedLanguage,
            success: function () {
                // On success, reload the page to apply the new language
                location.reload();
            }
        });
    }
