document.addEventListener("DOMContentLoaded", function () {
    // Check if the email sent modal should be shown
    const emailSentModalFlag = document.body.getAttribute("data-email-sent-modal");

    if (emailSentModalFlag === "true") {
        const emailSentModal = new bootstrap.Modal(document.getElementById('emailSentModal'), {
            keyboard: false
        });
        emailSentModal.show();
    }
});
