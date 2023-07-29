    function uploadFile(event) {

        // Показваме иконата за зареждане преди изпращането на заявката
        var loadingIcon = document.getElementById("loadingIcon");
        loadingIcon.style.display = 'block';

        axios.post(form.action, formData)
            .then(function (response) {

                loadingIcon.style.display = 'none';
            })
            .catch(function (error) {

                loadingIcon.style.display = 'none';
            });
    }
