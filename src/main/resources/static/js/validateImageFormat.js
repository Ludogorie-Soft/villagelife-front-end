function validateImageFormat(input) {
     var file = input.files[0];
     var allowedFormats = ["image/jpeg", "image/png"]
     if (!allowedFormats.includes(file.type)) {
         alert("Invalid format. Only JPG and PNG are allowed.");
         input.value = '';
     }
 }