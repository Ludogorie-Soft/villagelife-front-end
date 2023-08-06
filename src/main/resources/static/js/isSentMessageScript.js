document.addEventListener("DOMContentLoaded", function() {
  const messageContainer = document.getElementById("message");

  if (isSent) {
    messageContainer.textContent = "\u0421\u044a\u043e\u0431\u0449\u0435\u043d\u0438\u0435\u0442\u043e \u0431\u0435\u0448\u0435 \u0438\u0437\u043f\u0440\u0430\u0442\u0435\u043d\u043e \u0443\u0441\u043f\u0435\u0448\u043d\u043e!";
    messageContainer.classList.add("message-true");
  } else {
    messageContainer.textContent = "\u0421\u044a\u043e\u0431\u0449\u0435\u043d\u0438\u0435\u0442\u043e \u043d\u0435 \u0431\u0435\u0448\u0435 \u0438\u0437\u043f\u0440\u0430\u0442\u0435\u043d\u043e!";
    messageContainer.classList.add("message-false");
  }

  setTimeout(function() {
    messageContainer.style.animation = "fadeOut 2s linear";
  }, 5000);

  setTimeout(function() {
    messageContainer.style.display = "none";
  }, 7000);
});
