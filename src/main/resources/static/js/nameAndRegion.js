//function validateForm() {
//    var isVillageNameValid = validateVillageName();
//    var isRegionValid = validateRegion();
//
//    if (!isRegionValid.value ) {
//            document.getElementById('regionName-error').style.display = 'block';
//
//        return false;
//    }
//    if ( !isVillageNameValid) {
//            document.getElementById('villageName-error').style.display = 'block';
//
//        return false;
//    }
//
//    return true;
//}
function validateForm() {
    var isVillageNameValid = validateVillageName();
    var isRegionValid = validateRegion();
 resetErrorMessages();
    // Check if both inputs are valid
    if (!isRegionValid || !isVillageNameValid ) {
    console.log("1 if ")
        // Display error messages
        if (!isVillageNameValid) {
        console.log("village " + isVillageNameValid)
         document.getElementById('villageName-error').style.display = 'block';
//            displayErrorMessage('villageName-error', 'Please enter a village name.');
        }
        if (!isRegionValid.value) {
        console.log("region " + isRegionValid)
        document.getElementById('regionName-error').style.display = 'block';
//            displayErrorMessage('regionName-error', 'region.error.message');
        }
        return false;
    }

    return true;
}
//function displayErrorMessage(elementId, message) {
//    // Display error message for the specified element
//    var errorElement = document.getElementById(elementId);
//    if (errorElement) {
//        errorElement.textContent = message;
//        errorElement.style.display = 'block';
//    }
//}
  function validateRegion() {
        var regionSelect = document.getElementById('regionName');

        var region = regionSelect.value.trim();
        console.log("1 " + region)
        if (region === 'Избери област' ) {
            document.getElementById('regionName-error').style.display = 'block';

         console.log("2 " + region)
            return false;
        } else {
            document.getElementById('regionName-error').style.display = 'none';

         console.log("3 " + region)
            return true;
        }
    }
function resetErrorMessages() {
    // Hide all error messages
    var errorElements = document.querySelectorAll('.regionName-error');
    errorElements.forEach(function(element) {
        element.style.display = 'none';
    });
}
    function validateVillageName() {
        var villageNameInput = document.getElementById('villageName');
        var villageName = villageNameInput.value.trim();
        if (villageName.length < 1) {
            document.getElementById('villageName-error').style.display = 'block';
            return false;
        } else {
            document.getElementById('villageName-error').style.display = 'none';
            return true;
        }
    }


