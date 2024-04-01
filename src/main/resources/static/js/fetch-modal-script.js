  fetch('/filter/advancedSearchModalForm')
    .then(response => response.text())
    .then(html => {

      document.getElementById('modalContainer').innerHTML = html;
    })
    .catch(error => {
      console.error('Error loading page with modal window:', error);
    });