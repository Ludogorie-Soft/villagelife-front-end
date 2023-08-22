  fetch('/filter/advancedSearchModalForm')
    .then(response => response.text())
    .then(html => {

      document.getElementById('modalContainer').innerHTML = html;
    })
    .catch(error => {
      console.error('Грешка при зареждане на страницата с модалния прозорец:', error);
    });