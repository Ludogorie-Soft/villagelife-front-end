      $(document).ready(function() {
        $('#contact-form').submit(function(event) {
          event.preventDefault(); // Prevent the form from submitting normally

          // Store form element reference
          var $form = $(this);

          // Perform an AJAX post request to the form's action
          $.ajax({
            type: 'POST',
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function(response) {
              // Display success message
              $('#success-message').show();
              $form[0].reset(); // Clear the form
            },
            error: function() {
              // Display error message
              $('#error-message').show();
              $('#success-message').hide(); // Hide the success message
            }
          });
        });

        $('#submit-btn').click(function() {
          $('#success-message').hide(); // Hide the success message
          $('#error-message').hide(); // Hide the error message
        });
      });