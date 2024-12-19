
package raisetech.student.management2.controller;

import java.util.Arrays;
import java.util.List;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.controller.converter.StudentConverter;
import raisetech.student.management2.data.StudentsCourses;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

@RestController
public class StudentController {
  private StudentService service;
  private StudentConverter converter;


  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }


  @GetMapping("/studentList")
  public List<StudentDetail>  getStudentList() {
    //StudentDetailにまとめるのが依然と異なる。
    List<Student> students = service.searchStudentList();
    //生徒リストを取得
    List<StudentsCourses> cours = service.searchCourseList();
    // model.addAttribute("studentList",);//コースリストを取得

    return converter.convertStudentDetails(students, cours);

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }


  @GetMapping("/courseList")
  public List<StudentsCourses> getCourseList() {
    return service.searchCourseList();

  }

//  @GetMapping("/newStudent")
//  public String newStudent(Model model) {
//    StudentDetail studentDetail = new StudentDetail();
//    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
//    model.addAttribute("studentDetail", studentDetail);
//    return "registerStudent";
//  }
//難しい箇所👆登録処理が実装＞＞不要

  //上のメソッド何してるか？
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {



    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail) ;
  }
  //生徒一覧に一件をformから追加する
  //ここに何か処理入る。
  //下のDetailもおかし？

  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {

    //studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    return service.searchStudent(id);
  }
//難しい箇所👆AIツールの使い方

  //下、レッスン33
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {

    service.updateStudent(studentDetail);
    System.out.println(studentDetail.getStudent().getName() + "さんが新規受講生として登録されました。");
    return ResponseEntity.ok("更新処理が成功しました");
  }


  @GetMapping("/courseList/{studentId}")
  public List<StudentsCourses> getCourseList(@PathVariable Long studentId) {
    return service.searchCourseList();
    //引数消した
  }
}
