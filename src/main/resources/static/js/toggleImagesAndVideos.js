document.getElementById('toggleButton').addEventListener('click', function() {
    var images = document.getElementById('images');
    var videoContainer = document.getElementById('video-container');
    var icon = document.getElementById('toggleBtnIcon');
    var textVideo = document.getElementById('text-video');
    var textImages = document.getElementById('text-images');

    if (images.classList.contains('hidden')) {
        images.classList.remove('hidden');
        videoContainer.classList.add('hidden');

        icon.classList.remove('fa-regular', 'fa-image');
        icon.classList.add('fa-brands', 'fa-youtube', 'text-danger');

        textVideo.classList.remove('hidden');
        textImages.classList.add('hidden');

        if (player) {
            player.pauseVideo();
        }
    } else {
        images.classList.add('hidden');
        videoContainer.classList.remove('hidden');

        icon.classList.remove('fa-brands', 'fa-youtube', 'text-danger');
        icon.classList.add('fa-regular', 'fa-image');

        textImages.classList.remove('hidden');
        textVideo.classList.add('hidden');

        if (player) {
            player.playVideo();
        } else {
            loadVideo();
        }
    }
});