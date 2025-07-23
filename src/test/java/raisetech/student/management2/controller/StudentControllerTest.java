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
  void 受講生詳細の受講生でIDに数字以外を用いた時入力チェックにかかること() {
    Student student = new Student();
    student.setId("テストです");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertEquals(7,violations.size());
  }
}
//TODO 予想が7：実際5確認30分辺りからよくわからない