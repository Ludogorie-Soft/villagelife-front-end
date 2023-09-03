const toastLiveExample = document.getElementById('liveSubscriptionToast');
const toastBootstrap = new bootstrap.Toast(toastLiveExample);

if (document.querySelector('.toast-subscription-body').textContent.trim() !== '') {
  toastBootstrap.show();
}
