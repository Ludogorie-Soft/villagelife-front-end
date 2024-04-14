function validateConsents(sectionIds) {
        var isValid = true;

        sectionIds.forEach(function(sectionId) {
            var consentRadios = document.querySelectorAll('#' + sectionId + ' input[type="radio"].radio-button[required]');
            var consentChecked = true;

            consentRadios.forEach(function(radio) {
                var groupName = radio.getAttribute('name');
                var radiosInGroup = document.querySelectorAll('#' + sectionId + ' input[type="radio"][name="' + groupName + '"]');

                var checked = Array.prototype.slice.call(radiosInGroup).some(function(radio) {
                    return radio.checked;
                });

                if (!checked) {
                    consentChecked = false;
                }
            });

            var consentError = document.getElementById(sectionId + '-error');

            if (!consentChecked) {
                consentError.style.display = 'block';

                var firstUncheckedRadio = document.querySelector('#' + sectionId + ' input[type="radio"].radio-button[required]:not(:checked)');
                firstUncheckedRadio.focus();
                isValid = false;
            } else {
                consentError.style.display = 'none';
            }
        });

        return isValid;
    }

