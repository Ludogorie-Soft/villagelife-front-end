  fetch('/filter/advancedSearchModalForm')
    .then(response => response.text())
    .then(html => {
      // Вмъкване на съдържанието на "/advancedSearch" в основната страница
      document.getElementById('modalContainer').innerHTML = html;
    })
    .catch(error => {
      console.error('Грешка при зареждане на страницата с модалния прозорец:', error);
    });