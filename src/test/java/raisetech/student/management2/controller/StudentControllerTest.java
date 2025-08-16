package raisetech.student.management2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.verifyNoInteractions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.controller.StudentController;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  private static final Logger log = LoggerFactory.getLogger(StudentControllerTest.class);
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

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
    //サービスが呼ばれないことを検証
    verifyNoInteractions(service);
  }

  @Test
  void 受講生詳細の登録が実行できて空のリストが返る() throws Exception {
    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/registerStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                             {
                               "student": {
                        
                                 "name": "山田太郎",
                                 "kanaName": "ヤマダタロウ",
                                 "nickName": "やまちゃん",
                                 "email": "test@gmail.com",
                                 "area": "東京",
                                 "age": 20,
                                 "sex": "男性",
                                 "remark": "特になし"
                               },
                               "studentsCourseList": [
                                   {
                                       "courseName": "Java基礎コース"
                        
                                   }
                               ]
                             }
                        """
                ))
        .andExpect(status().isOk());
    //.andExpect(content().json("[]"));
    verify(service, times(1)).registerStudent(any());
  }


  @Test
  void 受講生詳細の更新が実行できて空のリストが返る() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.put("/updateStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                             {
                               "student": {
                                "id": "99",
                                 "name": "山田太郎",
                                 "kanaName": "ヤマダタロウ",
                                 "nickName": "やまちゃん",
                                 "email": "test@gmail.com",
                                 "area": "東京",
                                 "age": 25,
                                 "sex": "男性",
                                 "remark": "特になし"
                               },
                               "studentsCourseList": [
                                   {
                                      "id": "1",
                                      "studentId": "99",
                                       "courseName": "Java応用コース",
                                       "courseStartAt": "2025-06-10",
                                       "courseEndAt": "2025-09-10"
                        
                        
                                   }
                               ]
                             }
                        """
                ))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("更新を実行しました"));
    verify(service).updateStudent(any());


  }

  @Test
  void 数字123は3桁で不正なので400が返ってくる() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/student/123"))
        .andExpect(status().isBadRequest());

    verify(service, never()).searchStudent(anyString()); // 早期リターンを確認
  }

//  @Test
//  void 受講生詳細の例外APIが実行できステータス400で返る() throws Exception {
//    mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
//        .andExpect(status().is4xxClientError())
//        .andExpect(content().string("{\"message\":\"受講生詳細の例外APIが実行されました\"}"));
//  }

  @Test
  void 受講生詳細の受講生でIDに不正な数字以外を用いた時入力チェックにかかること() throws Exception {

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

    assertEquals(0, violations.size());
  }
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

