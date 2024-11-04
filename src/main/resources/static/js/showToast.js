const toastLive = document.getElementById('liveToast');
const toastBootstrap2 = new bootstrap.Toast(toastLive);
console.log(document.querySelector('.toast-body').textContent.trim() !== '');
if (document.querySelector('.toast-body').textContent.trim() !== '') {
  toastBootstrap2.show();
}