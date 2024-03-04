$(document).ready(function() {
    $(".option").click(function () {
        var selectedOption = $(this).data('value');
        if (selectedOption !== '') {
            var currentUrl = window.location.href;
            currentUrl = currentUrl.replace(/(\?|\&)lang=[^&]+/, '');
            currentUrl += (currentUrl.includes('?') ? '&' : '?') + 'lang=' + selectedOption;
            window.location.replace(currentUrl);
        }
    });
});

document.querySelector('.selected-option-container').addEventListener('click', function() {
  const optionsContainer = document.querySelector('.options-container');
  optionsContainer.style.display = optionsContainer.style.display === 'none' ? 'block' : 'none';
});

document.querySelectorAll('.option').forEach(option => {
  option.addEventListener('click', function() {
    document.querySelector('.selected-option').textContent = this.textContent;
    document.querySelector('.options-container').style.display = 'none';
  });
});

document.addEventListener("click", function(event) {
    const customSelect = document.querySelector(".custom-select");
    const optionsContainer = document.querySelector(".options-container");

    if (!customSelect.contains(event.target)) {
        optionsContainer.style.display = "none";
    }
});