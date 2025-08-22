// js/ui.js
import { students, courses } from './state.js';

export function renderDashboard() {
  const active = students.filter(s => !s.isDeleted).length;
  const avg = active > 0 ? Math.round(students.reduce((sum, s) => sum + s.age, 0) / active) : 0;

  document.getElementById('totalStudents').textContent = students.length;
  document.getElementById('totalCourses').textContent = courses.length;
  document.getElementById('activeStudents').textContent = active;
  document.getElementById('avgAge').textContent = avg;
}

export function renderStudents(list) {
  const grid = document.getElementById('studentsGrid');
  grid.innerHTML = list.map(student => createStudentCard(student)).join('');
}

export function renderCourses(list) {
  const grid = document.getElementById('coursesGrid');
  grid.innerHTML = list.map(c => createCourseCard(c)).join('');
}

export function renderCourseSelection() {
  const box = document.getElementById('courseSelection');
  if (!box) return;
  box.innerHTML = courses.map(c => `
    <div class="course-checkbox">
      <input type="checkbox" id="course-${c.id}" value="${c.name}">
      <label for="course-${c.id}">${c.name}</label>
    </div>
  `).join('');
}

function createStudentCard(student) {
  const initial = student.name?.charAt(0) || '';
  const coursesHtml = (student.courses || []).map(c => `<span class="course-tag">${c}</span>`).join('');

  return `
    <div class="student-card">
      <div class="student-avatar">${initial}</div>
      <div class="student-name">${student.name}</div>
      <div class="student-kana">${student.kanaName || ''}</div>

      <div class="student-info">
        <div class="info-item"><i class="fas fa-envelope"></i><span>${student.email}</span></div>
        <div class="info-item"><i class="fas fa-map-marker-alt"></i><span>${student.area ?? ''}</span></div>
        <div class="info-item"><i class="fas fa-birthday-cake"></i><span>${student.age}歳 (${student.sex ?? ''})</span></div>
        ${student.remark ? `<div class="info-item"><i class="fas fa-sticky-note"></i><span>${student.remark}</span></div>` : ''}
      </div>

      ${student.courses?.length ? `
      <div class="courses-section">
        <div class="courses-title"><i class="fas fa-book"></i> 受講コース</div>
        <div>${coursesHtml}</div>
      </div>` : ''}

      <div class="card-actions">
        <button class="btn btn-secondary btn-small" data-edit="${student.id}">
          <i class="fas fa-edit"></i> 編集
        </button>
        <button class="btn btn-secondary btn-small" data-view="${student.id}">
          <i class="fas fa-eye"></i> 詳細
        </button>
      </div>
    </div>
  `;
}

function createCourseCard(c) {
  return `
    <div class="student-card">
      <div class="student-avatar"><i class="fas fa-book"></i></div>
      <div class="student-name">${c.name}</div>
      <div class="student-info">
        <div class="info-item"><i class="fas fa-users"></i><span>${c.students}名が受講中</span></div>
      </div>
      <div class="card-actions">
        <button class="btn btn-secondary btn-small"><i class="fas fa-edit"></i> 編集</button>
        <button class="btn btn-secondary btn-small"><i class="fas fa-users"></i> 受講生一覧</button>
      </div>
    </div>
  `;
}
