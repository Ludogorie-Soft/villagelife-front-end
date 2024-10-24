document.addEventListener("DOMContentLoaded", function() {
      if (registrationModal === true) {
          var myModal = new bootstrap.Modal(document.getElementById('registerModal'));
          myModal.show();
      }
    });