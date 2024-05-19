/* adding extra rows for video url-s*/
function addVideoField() {
        var videoFields = document.getElementById("videoFields");
        var div = document.createElement("div");
        div.classList.add("mb-3");
        div.innerHTML = '<label for="videoUrl" class="form-label fw-bold" style="float: left">Моля, въведете URL на YouTube видео:</label>' +
            '<input class="form-control" name="videoUrl" type="text" placeholder="Пример: https://www.youtube.com/watch?v=VIDEO_ID">';
        videoFields.appendChild(div);
    }