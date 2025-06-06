package raisetech.student.management2.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management2.controller.converter.StudentConverter;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.exception.StudentNotFoundException;
import raisetech.student.management2.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  // Mock化、またはスタブ

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;
  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバータの処理が適切に呼び出せていること(){

    // 事前準備
    List<Student> studentList = new ArrayList<>();
    List<StudentsCourse> studentCoourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCoourseList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList();

    //  Assertions.assertEquals(expected, actual);
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCoourseList);


  }
  @Test
  void 受講生詳細検索_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    String studentId = "12345";
    Student student = new Student(studentId,"山田愛子", "やまだあいこ", "アイコ", "aiko@gmail.com","大阪",22,"女性","",false);
    student.setId(studentId);
    when(repository.searchStudent(studentId)).thenReturn(student);

    List<StudentsCourse> studentsCourses = new ArrayList<>();
    when(repository.searchStudentCourse(studentId)).thenReturn(studentsCourses);

    // 実行
    StudentDetail actual = sut.searchStudent(studentId);

    // 検証
    assertNotNull(actual);
    assertEquals(student, actual.getStudent());
    assertEquals(studentsCourses, actual.getStudentsCourseList());

    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(studentId);
  }

  @Test
  void 受講生詳細検索_存在しないIDを指定した場合_StudentNotFoundExceptionが発生すること() {
    // 事前準備
    String studentId = "99999";
    when(repository.searchStudent(studentId)).thenReturn(null);

    // 実行と検証
    Assertions.assertThrows(StudentNotFoundException.class, () -> {
      sut.searchStudent(studentId);
    });

    verify(repository, times(1)).searchStudent(studentId);
  }

  @Test
  void 受講生コース検索_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    List<StudentsCourse> expectedCourses = new ArrayList<>();
    when(repository.searchStudentCourseList()).thenReturn(expectedCourses);

    // 実行
    List<StudentsCourse> actual = sut.searchCourseList();

    // 検証
    assertEquals(expectedCourses, actual);
    verify(repository, times(1)).searchStudentCourseList();
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student("1","山田愛子", "やまだあいこ", "アイコ", "aiko@gmail.com","大阪",22,"女性","",false);
    student.setId("1");
    studentDetail.setStudent(student);

    List<StudentsCourse> studentsCourses = new ArrayList<>();
    StudentsCourse course = new StudentsCourse();
    course.setStudentId("1");
    studentsCourses.add(course);
    studentDetail.setStudentsCourseList(studentsCourses);

    // 実行
    sut.registerStudent(studentDetail);

    // 検証
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(course);
  }

  @Test
  void 受講生詳細の登録_学生がnullの場合はIllegalArgumentExceptionが発生すること() {
    // 事前準備
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(null);

    // 実行と検証
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      sut.registerStudent(studentDetail);
    });
  }

  @Test
  void 受講生コースの登録_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    StudentsCourse studentsCourse = new StudentsCourse();
    studentsCourse.setStudentId("1");

    // 実行
    sut.registerStudentCourse(studentsCourse);

    // 検証
    verify(repository, times(1)).registerStudentCourse(studentsCourse);
  }

  @Test
  void 受講生コースの登録_学生がnullの場合はIllegalArgumentExceptionが発生すること() {
    // 事前準備
    StudentsCourse studentsCourse = new StudentsCourse();
    studentsCourse.setStudentId(null);

    // 実行と検証
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      sut.registerStudentCourse(studentsCourse);
    });
  }

  @Test
  void 受講生の更新_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student("2", "佐藤太郎", "さとうたろう", "タロ", "taro@gmail.com", "東京", 25, "男性", "", false);
    StudentsCourse studentsCourse = new StudentsCourse();
    List<StudentsCourse> studentsCourses = List.of(studentsCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentsCourses);

    sut.updateStudent(studentDetail);
    // 検証
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentsCourse);
  }

//改善

}