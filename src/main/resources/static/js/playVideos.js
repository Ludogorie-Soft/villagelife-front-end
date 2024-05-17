var currentIndex = 0;
var player;
console.log("videos: " + videos);
function onYouTubeIframeAPIReady() {
    loadPlayer();
}

function loadPlayer() {
    player = new YT.Player('video-container', {
        width: '500',
        height: '390',
        videoId: getVideoId(videos[currentIndex].url),
        playerVars: {
            'autoplay': 1,
            'controls': 1,
            'mute': 1,
            'loop': 0,
            'playlist': videos.map(function(video) {
                return getVideoId(video.url);
            }).join(',')
        },
        events: {
            'onStateChange': onPlayerStateChange
        }
    });
}

function getVideoId(url) {
    var match = url.match(/v=([^&]+)/);
    return match ? match[1] : null;
}

function onPlayerStateChange(event) {
    if (event.data === YT.PlayerState.ENDED) {
        currentIndex++;
        if (currentIndex < videos.length) {
            player.loadVideoById(getVideoId(videos[currentIndex].url));
        } else {
            currentIndex = 0;  // Reset to the first video if the playlist ends
            player.loadVideoById(getVideoId(videos[currentIndex].url));
        }
    }
}
