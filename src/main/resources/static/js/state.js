// js/state.js
export let students = [];
export let courses = [];
export let currentModal = null;
export let editingStudentId = null;

export function setStudents(next) { students = next; }
export function setCourses(next)  { courses = next; }
export function setEditing(id)    { editingStudentId = id; }
export function setCurrentModal(t){ currentModal = t; }

// 初期モック
export function loadMockData() {
  students = [
    { id:"1", name:"田中 太郎", kanaName:"タナカ タロウ", nickName:"タロー",
      email:"tanaka@example.com", area:"東京", age:25, sex:"男性",
      remark:"プログラミング経験あり", isDeleted:false,
      courses:["Java基礎講座","Spring Boot入門"] },
    { id:"2", name:"佐藤 花子", kanaName:"サトウ ハナコ", nickName:"ハナ",
      email:"sato@example.com", area:"大阪", age:28, sex:"女性",
      remark:"デザイン経験あり", isDeleted:false,
      courses:["React入門","UI/UXデザイン"] },
    { id:"3", name:"山田 次郎", kanaName:"ヤマダ ジロウ", nickName:"ジロー",
      email:"yamada@example.com", area:"福岡", age:22, sex:"男性",
      remark:"新卒", isDeleted:false, courses:["HTML/CSS基礎"] },
    { id:"4", name:"鈴木 美咲", kanaName:"スズキ ミサキ", nickName:"ミサキ",
      email:"suzuki@example.com", area:"名古屋", age:24, sex:"女性",
      remark:"転職希望", isDeleted:false, courses:["Java基礎講座"] },
    { id:"5", name:"高橋 健一", kanaName:"タカハシ ケンイチ", nickName:"ケン",
      email:"takahashi@example.com", area:"札幌", age:30, sex:"男性",
      remark:"エンジニア歴3年", isDeleted:false,
      courses:["React入門","Spring Boot入門"] },
  ];

  // 受講者数再計算
  const courseCount = {};
  students.forEach(s => s.courses.forEach(cn => { courseCount[cn] = (courseCount[cn] || 0) + 1; }));
  const names = ["Java基礎講座","React入門","UI/UXデザイン","Spring Boot入門","HTML/CSS基礎","Node.js入門"];

  courses = names.map((name, i) => ({
    id: String(i+1),
    name,
    students: courseCount[name] || 0,
  }));
}

export function recomputeCourseStudents() {
  const cnt = {};
  students.forEach(s => s.courses.forEach(cn => { cnt[cn] = (cnt[cn] || 0) + 1; }));
  courses = courses.map(c => ({ ...c, students: cnt[c.name] || 0 }));
}
