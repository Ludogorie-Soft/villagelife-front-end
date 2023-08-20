      $(document).ready(function() {
        $('#contact-form').submit(function(event) {
          event.preventDefault(); // Prevent the form from submitting normally

          // Store form element reference
          var $form = $(this);


          $.ajax({
            type: 'POST',
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function(response) {

              $('#success-message').show();
              $form[0].reset();
            },
            error: function() {

              $('#error-message').show();
              $('#success-message').hide();
            }
          });
        });

        $('#submit-btn').click(function() {
          $('#success-message').hide();
          $('#error-message').hide();
        });
      });