var currentIndex = 0;
var player;

function loadVideo() {
    var videoIds = videos.map(function(video) {
        return getVideoId(video.url);
    }).join(',');

    var videoUrl = 'https://www.youtube.com/embed/' + getVideoId(videos[currentIndex].url) +
                   '?autoplay=1&controls=1&mute=1&loop=1&playlist=' + videoIds;

    var iframe = document.createElement('iframe');
    iframe.setAttribute('src', videoUrl);
    iframe.setAttribute('width', '100%');
    iframe.setAttribute('height', '100%');
    iframe.setAttribute('allowfullscreen', '');

    var videoContainer = document.getElementById('video-container');
    videoContainer.innerHTML = '';
    videoContainer.appendChild(iframe);
}

function getVideoId(url) {
    var match = url.match(/v=([^&]+)/);
    return match ? match[1] : null;
}

function onPlayerStateChange(event) {
    if (event.data === YT.PlayerState.ENDED) {
        currentIndex++;
        if (currentIndex < videos.length) {
            loadVideo();
        } else {
            currentIndex = 0;
            loadVideo();
        }
    }
}