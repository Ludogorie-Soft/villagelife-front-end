function validateForm() {
    var isVillageNameValid = validateVillageName();
    var isRegionValid = validateRegion();

    if (!isRegionValid || !isVillageNameValid ) {
    console.log("1 if ")

        if (!isVillageNameValid) {
        console.log("village " + isVillageNameValid)
         document.getElementById('villageName-error').style.display = 'block';

        }
        if (!isRegionValid) {
        console.log("region " + isRegionValid)
        document.getElementById('regionName-error').style.display = 'block';

        }
        return false;
    }

    return true;
}

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


