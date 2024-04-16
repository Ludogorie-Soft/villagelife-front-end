function validateForm() {
    var isVillageNameValid = validateVillageName();
    var isRegionValid = validateRegion();

    if (!isRegionValid || !isVillageNameValid ) {
        if (!isVillageNameValid) {
         document.getElementById('villageName-error').style.display = 'block';
         document.getElementById('villageName').scrollIntoView();
        }
        if (!isRegionValid) {
        document.getElementById('regionName-error').style.display = 'block';
        document.getElementById('regionName').scrollIntoView();
        }
        return false;
    }
    return true;
}

  function validateRegion() {
        var regionSelect = document.getElementById('regionName');

        var region = regionSelect.value.trim();
        if (region === 'Избери област' ) {
            document.getElementById('regionName-error').style.display = 'block';
            return false;
        } else {
            document.getElementById('regionName-error').style.display = 'none';
            return true;
        }
    }

function validateVillageName() {
    var villageNameInput = document.getElementById('villageName');
    var villageName = villageNameInput.value.trim();

    var cyrillicRegex = /^[а-яА-ЯЁё\s]+$/;

    if (!cyrillicRegex.test(villageName)) {
        document.getElementById('villageName-error').style.display = 'block';
        return false;
    } else {
        document.getElementById('villageName-error').style.display = 'none';
        return true;
    }
}

