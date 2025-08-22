// js/modals.js
import { students, setEditing, setCurrentModal } from './state.js';
import { renderCourseSelection } from './ui.js';

const $studentModal = () => document.getElementById('studentModal');
const $courseModal  = () => document.getElementById('courseModal');

export function openStudentModal(type, studentId = null) {
  setCurrentModal(type);
  setEditing(studentId);

  document.getElementById('modalTitle').textContent = type === 'add' ? '新規受講生登録' : '受講生情報編集';
  document.getElementById('studentForm').reset();

  renderCourseSelection();

  if (type === 'edit' && studentId) {
    const s = students.find(x => x.id === studentId);
    if (s) fillStudentForm(s);
  }
  $studentModal().classList.add('active');
}

export function closeStudentModal() {
  $studentModal().classList.remove('active');
  setCurrentModal(null);
  setEditing(null);
}

export function openCourseModal() {
  document.getElementById('courseForm').reset();
  document.getElementById('courseModalTitle').textContent = 'コース追加';
  $courseModal().classList.add('active');
}

export function closeCourseModal() {
  $courseModal().classList.remove('active');
}

export function getSelectedCourses() {
  return Array.from(document.querySelectorAll('#courseSelection input[type="checkbox"]:checked'))
    .map(cb => cb.value);
}

function fillStudentForm(s) {
  document.getElementById('studentName').value = s.name ?? '';
  document.getElementById('studentKana').value = s.kanaName ?? '';
  document.getElementById('studentNickname').value = s.nickName ?? '';
  document.getElementById('studentAge').value = s.age ?? '';
  document.getElementById('studentEmail').value = s.email ?? '';
  document.getElementById('studentArea').value = s.area ?? '';
  document.getElementById('studentGender').value = s.sex ?? '';
  document.getElementById('studentRemark').value = s.remark ?? '';

  // チェック反映
  setTimeout(() => {
    (s.courses || []).forEach(name => {
      const cb = document.querySelector(`#courseSelection input[value="${name}"]`);
      if (cb) cb.checked = true;
    });
  }, 0);
}
