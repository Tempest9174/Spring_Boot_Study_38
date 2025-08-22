// js/validation.js
import { notify } from './utils.js';

export function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

export function validateStudentForm({ name, email, age }) {
  if (!name?.trim()) { notify('名前を入力してください','error'); return false; }
  if (!email || !validateEmail(email)) { notify('有効なメールアドレスを入力してください','error'); return false; }
  if (!age || age < 1 || age > 100) { notify('有効な年齢を入力してください','error'); return false; }
  return true;
}
