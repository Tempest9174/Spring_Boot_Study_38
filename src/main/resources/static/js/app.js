// js/app.js
import { loadMockData, students, courses, setStudents, setCourses, editingStudentId, currentModal } from './state.js';
import { renderDashboard, renderStudents, renderCourses } from './ui.js';
import { openStudentModal, closeStudentModal, openCourseModal, closeCourseModal, getSelectedCourses } from './modals.js';
import { validateStudentForm } from './validation.js';
import { notify } from './utils.js';
import { Api } from './api.js';
import { recomputeCourseStudents } from './state.js';

function showLoadingStudents(show) {
  document.getElementById('loadingStudents').style.display = show ? 'block' : 'none';
}

function switchTab(name) {
  document.querySelectorAll('.nav-tab').forEach(b => b.classList.toggle('active', b.dataset.tab === name));
  document.querySelectorAll('.content-section').forEach(s => s.classList.toggle('active', s.id === name));

  if (name === 'students') loadStudents();
  else if (name === 'courses') loadCourses();
  else if (name === 'dashboard') renderDashboard();
}

async function loadStudents() {
  showLoadingStudents(true);
  try {
    const list = await Api.listStudents();
    // モック時は Api 側で state を更新してるので list は参照用
    renderStudents(list ?? students);
  } catch (e) {
    notify('データの取得に失敗しました', 'error');
  } finally {
    showLoadingStudents(false);
  }
}

async function loadCourses() {
  try {
    const list = await Api.listCourses();
    renderCourses(list ?? courses);
  } catch {
    notify('コースデータの取得に失敗しました', 'error');
  }
}

function filterStudents() {
  const q = document.getElementById('searchInput').value.toLowerCase();
  const filtered = students.filter(s =>
    (s.name||'').toLowerCase().includes(q)
    || (s.email||'').toLowerCase().includes(q)
    || (s.area||'').toLowerCase().includes(q)
    || (s.nickName||'').toLowerCase().includes(q)
  );
  renderStudents(filtered);
}

function filterCourses() {
  const q = document.getElementById('courseSearchInput').value.toLowerCase();
  const filtered = courses.filter(c => c.name.toLowerCase().includes(q));
  renderCourses(filtered);
}

function viewStudent(id) {
  const s = students.find(x => x.id === id);
  if (!s) return;
  alert(`受講生詳細:\n\n名前: ${s.name}\nメール: ${s.email}\n年齢: ${s.age}歳\n地域: ${s.area}\n\n受講コース: ${(s.courses||[]).join(', ')}`);
}

async function onStudentSubmit(ev) {
  ev.preventDefault();

  const body = {
    id: editingStudentId ?? undefined,
    name: document.getElementById('studentName').value,
    kanaName: document.getElementById('studentKana').value,
    nickName: document.getElementById('studentNickname').value,
    age: Number(document.getElementById('studentAge').value),
    email: document.getElementById('studentEmail').value,
    area: document.getElementById('studentArea').value,
    sex: document.getElementById('studentGender').value,
    remark: document.getElementById('studentRemark').value,
    isDeleted: false,
    courses: getSelectedCourses(),
  };

  if (!validateStudentForm(body)) return;

  try {
    if (currentModal === 'edit' && editingStudentId) {
      await Api.updateStudent({ ...body, id: editingStudentId });
      notify('受講生情報を更新しました！');
    } else {
      await Api.saveStudent(body);
      notify('新しい受講生を登録しました！');
    }
    closeStudentModal();
    recomputeCourseStudents();
    await loadStudents();
    renderDashboard();
    await loadCourses();
  } catch {
    notify('保存に失敗しました', 'error');
  }
}

function delegateCardClicks() {
  document.getElementById('studentsGrid').addEventListener('click', (e) => {
    const editBtn = e.target.closest('[data-edit]');
    const viewBtn = e.target.closest('[data-view]');
    if (editBtn) {
      const id = editBtn.getAttribute('data-edit');
      openStudentModal('edit', id);
    } else if (viewBtn) {
      const id = viewBtn.getAttribute('data-view');
      viewStudent(id);
    }
  });
}

function enhanceButtonsLoading() {
  document.addEventListener('click', (e) => {
    const btn = e.target.closest('.btn');
    if (!btn || btn.classList.contains('loading')) return;
    const original = btn.innerHTML;
    btn.classList.add('loading');
    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 処理中...';
    setTimeout(() => {
      btn.classList.remove('loading');
      btn.innerHTML = original;
    }, 1000);
  });
}

function wireShortcuts() {
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
      if (document.getElementById('studentModal').classList.contains('active')) closeStudentModal();
      if (document.getElementById('courseModal').classList.contains('active')) closeCourseModal();
    }
    if (e.ctrlKey && e.key === 'n') { e.preventDefault(); openStudentModal('add'); }
    if (e.ctrlKey && e.key === '/') { e.preventDefault(); document.getElementById('searchInput').focus(); }
  });
}

function wireModalsOutsideClick() {
  document.getElementById('studentModal').addEventListener('click', (e) => { if (e.target.id === 'studentModal') closeStudentModal(); });
  document.getElementById('courseModal').addEventListener('click', (e) => { if (e.target.id === 'courseModal') closeCourseModal(); });
}

function wireTabs() {
  document.querySelectorAll('.nav-tab').forEach(btn => {
    btn.addEventListener('click', () => switchTab(btn.dataset.tab));
  });
}

function wireFormsAndButtons() {
  document.getElementById('studentForm').addEventListener('submit', onStudentSubmit);
  document.getElementById('courseForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const name = document.getElementById('courseName').value.trim();
    if (!name) { notify('コース名を入力してください', 'error'); return; }
    if (courses.some(c => c.name === name)) { notify('同じ名前のコースが既に存在します', 'error'); return; }

    const next = [...courses, {
      id: String(courses.length + 1),
      name,
      students: 0,
      startDate: document.getElementById('courseStartDate').value,
      endDate: document.getElementById('courseEndDate').value,
      description: document.getElementById('courseDescription').value,
    }];
    setCourses(next);
    notify('新しいコースを追加しました！');
    closeCourseModal();
    renderCourseSelection();
    renderCourses(next);
  });

  document.getElementById('addStudentBtn').addEventListener('click', () => openStudentModal('add'));
  document.getElementById('addCourseBtn').addEventListener('click', () => openCourseModal());
  document.getElementById('studentModalClose').addEventListener('click', closeStudentModal);
  document.getElementById('courseModalClose').addEventListener('click', closeCourseModal);

  document.getElementById('searchInput').addEventListener('keyup', filterStudents);
  document.getElementById('courseSearchInput').addEventListener('keyup', filterCourses);
}

function renderCourseSelection() {
  // helper: re-export to keep app.js self-contained
  import('./ui.js').then(m => m.renderCourseSelection());
}

// ---- 起動 ----
document.addEventListener('DOMContentLoaded', async () => {
  loadMockData();            // 初回はモックで即表示
  renderDashboard();
  await loadStudents();
  await loadCourses();

  wireTabs();
  wireFormsAndButtons();
  wireShortcuts();
  wireModalsOutsideClick();
  delegateCardClicks();
  enhanceButtonsLoading();
});


// vite.config.js
export default {
  server: {
    proxy: {
      '/api': { target: 'http://localhost:8083', changeOrigin: true }
    }
  }
}
