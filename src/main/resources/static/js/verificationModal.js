document.addEventListener("DOMContentLoaded", function() {
    var verificationModal = document.getElementById('verificationModal').getAttribute('data-show-modal') === 'true';
    if (verificationModal) {
        var myModal = new bootstrap.Modal(document.getElementById('verificationModal'));
        myModal.show();
    }
});