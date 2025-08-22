package raisetech.student.management2.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
// 以下のインポート文を修正・追加
import raisetech.student.management2.data.StudentsCourse; // 正しいStudentsCourseのインポート
import raisetech.student.management2.data.Student;       // 正しいStudentのインポート
import raisetech.student.management2.domain.StudentDetail; // 正しいStudentDetailのインポート

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }
  @Nested
  @DisplayName("コースが見つかる場合のテスト")
  class CourseFoundTest {

    @Test
    @DisplayName("受講生に紐づくコースが複数ある場合、正しくマッピングされること")
    void testConvertStudentDetails_withMultipleCourses() {
      // Given (前提条件)
      // 学生リストを作成
      // Studentクラスのコンストラクタに合わせて引数を渡す
      Student student1 = new Student(); // NoArgsConstructorを使用
      student1.setId("S001");
      student1.setName("田中太郎");
      student1.setKanaName("たなかたろう");
      student1.setAge(25);
      student1.setSex("男性");
      student1.setArea("東京都新宿区");
      student1.setEmail("taro@gmail.com");

      Student student2 = new Student();
      student2.setId("S002");
      student2.setName("佐藤花子");

      List<Student> studentList = List.of(student1, student2);

      // コースリストを作成 (S001に2つ、S002に0つ)
      List<StudentsCourse> studentCourseList = new ArrayList<>();
      StudentsCourse course1 = new StudentsCourse(); // NoArgsConstructorを使用
      course1.setId("C001");
      course1.setCourseName("Java入門");
      course1.setStudentId("S001");
      studentCourseList.add(course1);

      StudentsCourse course2 = new StudentsCourse();
      course2.setId("C002");
      course2.setCourseName("Spring Boot基礎");
      course2.setStudentId("S001");
      studentCourseList.add(course2);

      // When (テスト対象メソッドの実行)
      List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

      // Then (検証)
      // 結果のリストサイズが正しいか確認
      assertEquals(2, result.size());

      // 1人目の学生の詳細情報を検証 (田中太郎)
      StudentDetail detail1 = result.get(0);
      assertEquals(student1.getId(), detail1.getStudent().getId());
      assertEquals(2, detail1.getStudentsCourseList().size()); // 紐づくコースが2つあること

      // 2人目の学生の詳細情報を検証 (佐藤花子)
      StudentDetail detail2 = result.get(1);
      assertEquals(student2.getId(), detail2.getStudent().getId());
      assertEquals(0, detail2.getStudentsCourseList().size()); // 紐づくコースがないこと
    }
  }



  @Nested
  @DisplayName("受講生コースが見つからない場合のテスト")
  class CourseNotFoundTest {

    @Test
    @DisplayName("受講生に紐づくコース情報が1つもない場合、空のリストが設定されること")
    void testConvertStudentDetails_noCoursesFound() {
      // Given
      Student student1 = new Student();
      student1.setId("S003");
      student1.setName("山田健");
      List<Student> studentList = List.of(student1);

      // 空のコースリスト
      List<StudentsCourse> studentCourseList = Collections.emptyList();

      // When
      List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

      // Then
      assertEquals(1, result.size());
      StudentDetail detail = result.get(0);
      assertEquals(student1.getId(), detail.getStudent().getId());
      assertEquals(0, detail.getStudentsCourseList().size()); // コースリストが空であること
    }

    @Test
    @DisplayName("受講生リスト自体が空の場合、結果も空のリストが返されること")
    void testConvertStudentDetails_emptyStudentList() {
      // Given
      List<Student> studentList = Collections.emptyList();
      StudentsCourse dummyCourse = new StudentsCourse(); // ダミーコースを生成
      dummyCourse.setId("C003");
      dummyCourse.setCourseName("Ruby");
      dummyCourse.setStudentId("S004");
      List<StudentsCourse> studentCourseList = List.of(dummyCourse);

      // When
      List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

      // Then
      assertEquals(0, result.size());
    }
  }
}
