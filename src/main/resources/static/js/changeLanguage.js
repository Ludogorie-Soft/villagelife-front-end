    function changeLanguage() {
        var selectedLanguage = document.getElementById("language-select").value;

        $.ajax({
            type: "GET",
            url: "/locale?lang=" + selectedLanguage,
            success: function () {

                location.reload();
            }
        });
    }
