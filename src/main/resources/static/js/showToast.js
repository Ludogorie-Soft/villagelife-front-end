const toastLiveExample = document.getElementById('liveToast');
const toastBootstrap = new bootstrap.Toast(toastLiveExample);

if (document.querySelector('.toast-body').textContent.trim() !== '') {
  toastBootstrap.show();
}