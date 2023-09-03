        var slideIndex = 0;
        showSlides();

        function showSlides() {
            var slides = document.getElementsByClassName("mySlides");
            for (var i = 0; i < slides.length; i++) {
                slides[i].classList.remove("active");
            }

            slideIndex++;
            if (slideIndex > slides.length) { slideIndex = 1; }
            slides[slideIndex - 1].classList.add("active");

            setTimeout(showSlides, 5000);
        }