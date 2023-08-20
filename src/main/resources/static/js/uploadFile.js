    function uploadFile(event) {

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
