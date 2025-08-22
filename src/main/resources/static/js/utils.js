// js/utils.js
export function notify(message, type='success') {
  const n = document.createElement('div');
  n.className = 'notification';
  n.innerHTML = `<i class="fas fa-${type==='error'?'exclamation-circle':'check-circle'}"></i> ${message}`;
  document.body.appendChild(n);
  setTimeout(() => {
    n.style.animation = 'notificationSlideIn 0.3s ease reverse';
    setTimeout(() => n.remove(), 300);
  }, 3000);
}

export function formatDate(dateString) {
  if (!dateString) return '';
  const d = new Date(dateString);
  return d.toLocaleDateString('ja-JP', { year:'numeric', month:'long', day:'numeric' });
}
