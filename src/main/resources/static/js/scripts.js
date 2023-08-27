
const formContainer = document.getElementById('form-container');

function addForm() {
  const form = document.createElement('form');
  // add form fields to the new form element
  formContainer.appendChild(form);
}

function showConfirmation(button) {
            if (confirm("Are you sure you want to delete this village?")) {
                var form = button.nextElementSibling;
                form.submit();
            } else {
            }
        }

