// js/api.js
import { students, courses, setStudents, setCourses } from './state.js';

const USE_MOCK = false;                 // ← 本番接続時は false に
const BASE_URL = '';                   // 同一オリジンなら空。別配信なら 'http://localhost:8080'
const headers = { 'Content-Type': 'application/json' };

async function request(path, init) {
  const res = await fetch(`${BASE_URL}${path}`, init);
  if (!res.ok) throw new Error(await res.text().catch(()=>res.statusText));
  const ct = res.headers.get('Content-Type') || '';
  return ct.includes('application/json') ? res.json() : null;
}

// ---- Mock 実装 ----
function mockListStudents() { return Promise.resolve([...students]); }
function mockGetStudent(id) { return Promise.resolve(students.find(s => s.id === id) || null); }
function mockSaveStudent(body) {
  const next = [...students, { id: String(students.length + 1), ...body }];
  setStudents(next);
  return Promise.resolve(next.at(-1));
}
function mockUpdateStudent(body) {
  const idx = students.findIndex(s => s.id === body.id);
  if (idx < 0) return Promise.resolve(null);
  const next = [...students]; next[idx] = { ...next[idx], ...body }; setStudents(next);
  return Promise.resolve(next[idx]);
}
function mockListCourses()  { return Promise.resolve([...courses]); }

// ---- Public API ----
export const Api = {
  listStudents: () => USE_MOCK ? mockListStudents() : request('/api/students'),
  getStudent:   (id) => USE_MOCK ? mockGetStudent(id) : request(`/api/students/${encodeURIComponent(id)}`),
  saveStudent:  (body) => USE_MOCK ? mockSaveStudent(body) : request('/api/students', { method:'POST', headers, body: JSON.stringify(body) }),
  updateStudent:(body) => USE_MOCK ? mockUpdateStudent(body) : request(`/api/students/${encodeURIComponent(body.id)}`, { method:'PUT', headers, body: JSON.stringify(body) }),
  listCourses:  () => USE_MOCK ? mockListCourses() : request('/api/courses'),
};
