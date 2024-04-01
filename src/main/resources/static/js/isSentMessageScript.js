document.addEventListener("DOMContentLoaded", function() {
  const messageContainer = document.getElementById("message");

  if (isSent) {
    messageContainer.textContent = "The message was sent successfully!";
    messageContainer.classList.add("message-true");
  } else {
    messageContainer.textContent = "The message was not sent!";
    messageContainer.classList.add("message-false");
  }

  setTimeout(function() {
    messageContainer.style.animation = "fadeOut 2s linear";
  }, 5000);

  setTimeout(function() {
    messageContainer.style.display = "none";
  }, 7000);
});
