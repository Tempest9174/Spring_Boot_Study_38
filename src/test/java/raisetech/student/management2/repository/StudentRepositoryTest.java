package raisetech.student.management2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.data.StudentsCourse;

@MybatisTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索ができる事() {

    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(10);

  }


  @Test
  void 受講生の登録ができること() {
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(11);
  }


  @Test
  void 受講生の登録が正しく完了している事() {
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    //登録した受講生が正しく元のまま登録されているか
    Student registeredStudent = actual.get(actual.size() - 1);
    assertThat(registeredStudent.getName()).isEqualTo("山田太郎");
    assertThat(registeredStudent.getKanaName()).isEqualTo("ヤマダタロウ");
    assertThat(registeredStudent.getNickName()).isEqualTo("やまちゃん");
    assertThat(registeredStudent.getEmail()).isEqualTo("test@gmail.com");
    assertThat(registeredStudent.getArea()).isEqualTo("東京");
    assertThat(registeredStudent.getAge()).isEqualTo(20);
    assertThat(registeredStudent.getSex()).isEqualTo("男性");
    assertThat(registeredStudent.isDeleted()).isEqualTo(false);
  }
  @Test
  void 受講生のIDで単一検索ができる事(){
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    Student actual = sut.searchStudent("1");
    assertThat(actual.getName()).isEqualTo("山田太郎");
  }
  @Test
  void 受講生IDからコース情報を検索できること() {
    // --- 準備 ---
    // テストDB（H2など）に事前にデータがあると仮定

    String targetStudentId = "1";

    // --- 実行 ---
    List<StudentsCourse> actualList = sut.searchStudentCourse(targetStudentId);

    // --- 検証 ---
    assertThat(actualList).isNotNull();              // nullではない
    assertThat(actualList).isNotEmpty();             // 空でない
    assertThat(actualList.get(0).getStudentId()).isEqualTo("1");  // ID一致
    assertThat(actualList.get(0).getCourseName()).contains("Java基礎"); // コース名確認
  }

  @Test
  void 存在しない受講生IDでは空リストが返ること() {
    // --- 実行 ---
    List<StudentsCourse> actualList = sut.searchStudentCourse("9999");

    // --- 検証 ---
    assertThat(actualList).isEmpty(); // データなし
  }

  @Test
  void 受講生コース情報の登録が正しくできること(){
    StudentsCourse studentsCourse = new StudentsCourse();
    studentsCourse.setStudentId("1");
    studentsCourse.setCourseName("Java基礎コース");
    studentsCourse.setCourseStartAt(java.sql.Date.valueOf("2024-07-01"));
    studentsCourse.setCourseEndAt(java.sql.Date.valueOf("2024-09-30"));

    sut.registerStudentCourse(studentsCourse);

    List<StudentsCourse> actualList = sut.searchStudentCourse("1");
    StudentsCourse registeredCourse = actualList.get(actualList.size() - 1);

    assertThat(registeredCourse.getStudentId()).isEqualTo("1");
    assertThat(registeredCourse.getCourseName()).isEqualTo("Java基礎コース");
    assertThat(registeredCourse.getCourseStartAt()).isEqualTo(java.sql.Date.valueOf("2024-07-01"));
    assertThat(registeredCourse.getCourseEndAt()).isEqualTo(java.sql.Date.valueOf("2024-09-30"));
  }

  @Test
  void 受講生の情報の更新が正しく行われること(){
    // --- 準備 ---
    Student before = sut.searchStudent("1");
    assertThat(before.getEmail()).isEqualTo("taro@example.com");

    // --- 実行: 更新処理 ---
    Student updated = new Student();
    updated.setId("1");
    updated.setName("山田太郎");
    updated.setKanaName("ヤマダタロウ");
    updated.setNickName("たろう");
    updated.setEmail("new@example.com");
    updated.setArea("大阪");
    updated.setAge(26);
    updated.setSex("男");
    updated.setRemark("変更後");
    updated.setDeleted(false);

    sut.updateStudent(updated);

    // --- 検証: 更新結果を再取得 ---
    Student after = sut.searchStudent("1");

    assertThat(after.getEmail()).isEqualTo("new@example.com");
    assertThat(after.getArea()).isEqualTo("大阪");
    assertThat(after.getAge()).isEqualTo(26);
    assertThat(after.getRemark()).isEqualTo("変更後");
  }
  @Test
  void 受講生コース情報のコース名更新が正しく行われること(){
    // --- 準備 ---
    List<StudentsCourse> beforeList = sut.searchStudentCourse("1");
    StudentsCourse before = beforeList.get(0);
    assertThat(before.getCourseName()).isEqualTo("Java基礎講座");

    // --- 実行: 更新処理 ---
    before.setCourseName("Java応用コース");
    sut.updateStudentCourse(before);

    // --- 検証: 更新結果を再取得 ---
    List<StudentsCourse> afterList = sut.searchStudentCourse("1");
    StudentsCourse after = afterList.get(0);

    assertThat(after.getCourseName()).isEqualTo("Java応用コース");
  }

  

}


