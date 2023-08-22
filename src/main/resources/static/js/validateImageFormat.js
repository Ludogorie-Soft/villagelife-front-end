    function validateImageFormat(input) {
        var file = input.files[0];
        var allowedFormats = ["image/jpeg", "image/png"];

        if (!allowedFormats.includes(file.type)) {
            alert("Невлиден формат. Разрешени са само JPG и PNG.");
            input.value = '';
        }
    }