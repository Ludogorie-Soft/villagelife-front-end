    var video1 = document.getElementById("video1");
    var video2 = document.getElementById("video2");

    video1.addEventListener("ended", function() {
      video2.play();
    });