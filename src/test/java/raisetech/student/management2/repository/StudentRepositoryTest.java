package raisetech.student.management2.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.data.StudentsCourse;

@MybatisTest
@Sql(scripts = {"/repository-schema.sql", "/repository-data.sql"})
class StudentRepositoryTest {

  @Autowired
  private StudentRepository repository;

  @Test
  void 正常系_受講生の一覧を検索できること() {
    List<Student> students = repository.search();

    assertEquals(10, students.size());
    assertEquals(
        Set.of(
            "山田太郎",
            "佐藤花子",
            "鈴木健",
            "高橋美咲",
            "田中大輔",
            "伊藤桜",
            "渡辺翔太",
            "小林真由",
            "加藤直樹",
            "吉田愛"),
        students.stream().map(Student::getName).collect(java.util.stream.Collectors.toSet()));
  }

  @Test
  void 正常系_IDを指定して受講生を検索できること() {
    Student student = repository.searchStudent("1");

    assertNotNull(student);
    assertEquals("1", student.getId());
    assertEquals("山田太郎", student.getName());
    assertEquals("taro@example.com", student.getEmail());
  }

  @Test
  void 異常系_存在しないIDの受講生を検索するとnullが返ること() {
    assertNull(repository.searchStudent("999"));
  }

  @Test
  void 正常系_受講生を登録でき自動採番されること() {
    Student student = new Student(
        null,
        "鈴木一郎",
        "いちろう",
        "スズキイチロウ",
        "ichiro@example.com",
        "福岡",
        28,
        "男性",
        "新規登録",
        false);

    repository.registerStudent(student);

    assertNotNull(student.getId());
    Student registered = repository.searchStudent(student.getId());
    assertEquals("鈴木一郎", registered.getName());
    assertEquals("ichiro@example.com", registered.getEmail());
    assertFalse(registered.isDeleted());
  }

  @Test
  void 正常系_受講生コースの一覧を検索できること() {
    List<StudentsCourse> courses = repository.searchStudentCourseList();

    assertEquals(7, courses.size());
  }

  @Test
  void 正常系_受講生IDに紐づくコースだけを検索できること() {
    List<StudentsCourse> courses = repository.searchStudentCourse("1");

    assertEquals(2, courses.size());
    assertTrue(courses.stream().allMatch(course -> "1".equals(course.getStudentId())));
  }

  @Test
  void 正常系_受講生コースを登録でき自動採番されること() {
    StudentsCourse course = new StudentsCourse();
    course.setStudentId("2");
    course.setCourseName("Docker");
    course.setCourseStartAt(Date.valueOf(LocalDate.of(2026, 4, 1)));
    course.setCourseEndAt(Date.valueOf(LocalDate.of(2027, 3, 31)));

    repository.registerStudentCourse(course);

    assertNotNull(course.getId());
    List<StudentsCourse> courses = repository.searchStudentCourse("2");
    assertEquals(2, courses.size());
    assertTrue(courses.stream().anyMatch(registered -> "Docker".equals(registered.getCourseName())));
  }

  @Test
  void 正常系_受講生情報を更新できること() {
    Student student = repository.searchStudent("1");
    student.setName("山田次郎");
    student.setEmail("jiro@example.com");

    repository.updateStudent(student);

    Student updated = repository.searchStudent("1");
    assertEquals("山田次郎", updated.getName());
    assertEquals("jiro@example.com", updated.getEmail());
  }

  @Test
  void 正常系_受講生を論理削除できること() {
    Student student = repository.searchStudent("1");
    student.setDeleted(true);

    repository.updateStudent(student);

    Student deleted = repository.searchStudent("1");
    assertTrue(deleted.isDeleted());
  }

  @Test
  void 異常系_存在しない受講生IDのコース検索は空リストが返ること() {
    List<StudentsCourse> courses = repository.searchStudentCourse("999");

    assertTrue(courses.isEmpty());
  }

  @Test
  void 異常系_名前がnullの受講生は登録できないこと() {
    Student student = new Student(
        null,
        null,
        "てすと",
        "テスト",
        "invalid@example.com",
        "東京",
        20,
        "男性",
        null,
        false);

    assertThrows(
        DataIntegrityViolationException.class,
        () -> repository.registerStudent(student));
  }

  @Test
  void 異常系_コース名がnullのコースは登録できないこと() {
    StudentsCourse course = new StudentsCourse();
    course.setStudentId("1");
    course.setCourseName(null);

    assertThrows(
        DataIntegrityViolationException.class,
        () -> repository.registerStudentCourse(course));
  }

  @Test
  void 異常系_存在しない受講生IDのコースは登録できないこと() {
    StudentsCourse course = new StudentsCourse();
    course.setStudentId("999");
    course.setCourseName("Java基礎");

    assertThrows(
        DataIntegrityViolationException.class,
        () -> repository.registerStudentCourse(course));
  }
}

