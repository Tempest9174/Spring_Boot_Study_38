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
import raisetech.student.management2.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
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

}