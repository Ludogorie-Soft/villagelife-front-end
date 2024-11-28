document.getElementById('userSearchDataModal').addEventListener('show.bs.modal', function () {
        const regionName = document.getElementById('regionName').value.trim() || null;
        const villageName = document.getElementById('search-input').value.trim() || null;

        if (villageName && !regionName) {
        event.preventDefault();

        const currentLocale = /*[[${currentLocale}]]*/;
        if (currentLocale === 'bg') {
            alert('Моля, изберете регион за посоченото име на село.');
        } else if(currentLocale === 'en') {
            alert('Please select a region for the specified village name.');
        } else if(currentLocale === 'de') {
            alert('Bitte wählen Sie eine Region für den angegebenen Dorfnamen aus.');
        }
        return;
    }

        const propertyTypes = Array.from(document.querySelectorAll('[name="propertyTypes"]:checked')).map(el => el.value);
        const propertyTransferType = document.querySelector('[name="propertyTransferType"]:checked')?.value || '';
        const minBuiltUpArea = document.getElementById('minBuiltUpArea').value;
        const maxBuiltUpArea = document.getElementById('maxBuiltUpArea').value;
        const minYardArea = document.getElementById('minYardArea').value;
        const maxYardArea = document.getElementById('maxYardArea').value;
        const minRoomsCount = document.getElementById('minRoomsCount').value;
        const maxRoomsCount = document.getElementById('maxRoomsCount').value;
        const minBathroomsCount = document.getElementById('minBathroomsCount').value;
        const maxBathroomsCount = document.getElementById('maxBathroomsCount').value;
        const heating = Array.from(document.querySelectorAll('[name="heating"]:checked')).map(el => el.value);
        const constructionTypes = Array.from(document.querySelectorAll('[name="constructionTypes"]:checked')).map(el => el.value);
        const propertyConditions = Array.from(document.querySelectorAll('[name="propertyConditions"]:checked')).map(el => el.value);
        const minConstructionYear = document.getElementById('minConstructionYear').value;
        const maxConstructionYear = document.getElementById('maxConstructionYear').value;
        const minPrice = document.getElementById('minPrice').value;
        const maxPrice = document.getElementById('maxPrice').value;
        const minRentPrice = document.getElementById('minRentPrice').value;
        const maxRentPrice = document.getElementById('maxRentPrice').value;
        const ownershipTypes = Array.from(document.querySelectorAll('[name="ownershipTypes"]:checked')).map(el => el.value);

        document.getElementById('saveRegionName').value = regionName;
        document.getElementById('saveVillageName').value = villageName;
        document.getElementById('savePropertyTypes').value = propertyTypes.join(',');
        document.getElementById('savePropertyTransferType').value = propertyTransferType;
        document.getElementById('saveMinBuiltUpArea').value = minBuiltUpArea;
        document.getElementById('saveMaxBuiltUpArea').value = maxBuiltUpArea;
        document.getElementById('saveMinYardArea').value = minYardArea;
        document.getElementById('saveMaxYardArea').value = maxYardArea;
        document.getElementById('saveMinRoomsCount').value = minRoomsCount;
        document.getElementById('saveMaxRoomsCount').value = maxRoomsCount;
        document.getElementById('saveMinBathroomsCount').value = minBathroomsCount;
        document.getElementById('saveMaxBathroomsCount').value = maxBathroomsCount;
        document.getElementById('saveHeating').value = heating.join(',');
        document.getElementById('saveConstructionTypes').value = constructionTypes.join(',');
        document.getElementById('savePropertyConditions').value = propertyConditions.join(',');
        document.getElementById('saveMinConstructionYear').value = minConstructionYear;
        document.getElementById('saveMaxConstructionYear').value = maxConstructionYear;
        document.getElementById('saveMinPrice').value = minPrice;
        document.getElementById('saveMaxPrice').value = maxPrice;
        document.getElementById('saveMinRentPrice').value = minRentPrice;
        document.getElementById('saveMaxRentPrice').value = maxRentPrice;
        document.getElementById('saveOwnershipTypes').value = ownershipTypes.join(',');
    });