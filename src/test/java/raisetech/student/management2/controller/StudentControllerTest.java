package raisetech.student.management2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行でき空のリストが返る() throws Exception {
    when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());
    // .andExpect(content().json("[{\"student\":null,\"studentsCourseList\":null}]"));

    verify(service, times(1)).searchStudentList();
  }


  @Test
  void 受講生詳細の検索が実行できて空で返ってくる() throws Exception {
    String id = "99";
    when(service.searchStudent(id)).thenReturn(new StudentDetail());
    mockMvc.perform(MockMvcRequestBuilders.get("/student/" + id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の検索が実行できずに400エラーで返ってくる() throws Exception {
    String id = "100";
    when(service.searchStudent(id)).thenReturn(new StudentDetail());
    mockMvc.perform(MockMvcRequestBuilders.get("/student/" + id))
        .andExpect(status().isBadRequest());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の受講生でIDに不正な数字以外を用いた時入力チェックにかかること() {

    Student student = new Student();
    //TODO セットメソッドを書く
    student.setId("テストです");
    student.setName("山田太郎");
    student.setNickName("やまちゃん");
    student.setKanaName("ヤマダタロウ");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");


    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertEquals(0,violations.size());
  }

//TODO 予想が7：実際5確認30分辺りからよくわからない　AssertThat
//  @Test
//  void 受講生の詳細登録において実行ができて空のリストが返る() throws Exception {
//    StudentDetail studentDetail = new StudentDetail();
//
//    when(service.registerStudent(any(studentDetail.class))).thenReturn(List.of(studentDetail()));
//    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent"))
//      .andExpect(status().isOk())
//      .andExpect(content().json(""));
//  }
}