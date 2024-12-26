
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

/**
 * 受講生の検索や登録、更新等を行うREST APIとして実行するコントローラ
 */


@RestController
public class StudentController {
  private StudentService service;

  /**
   * コンストラクタ
   * @param service
   * @param
   */

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索
   * 全件検索を行う。条件指定は行わないもの。
   * @return 受講生一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail>  getStudentList() {
    //StudentDetailにまとめるのが依然と異なる。

    // model.addAttribute("studentList",);//コースリストを取得

    return service.searchStudentList();

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }

  /**
   * 受講生検索を行う
   * IDに紐づくに似の受講生の情報を取得する
   * @param id 受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {

    //studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    return service.searchStudent(id);
  }
//難しい箇所👆AIツールの使い方



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
