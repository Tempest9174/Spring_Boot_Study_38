package raisetech.student.management2.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management2.controller.converter.StudentConverter;
import raisetech.student.management2.data.StudentsCourses;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

@Controller
public class StudentController {
  private StudentService service;
  private StudentConverter converter;


  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }


  @GetMapping("/studentList")
  public String  getStudentList(Model model) {
    //StudentDetailにまとめるのが依然と異なる。
    List<Student> students = service.searchStudentList();
    //生徒リストを取得
    List<StudentsCourses> cours = service.searchCourseList();
    model.addAttribute("studentList",converter.convertStudentDetails(students, cours));//コースリストを取得

    return "studentList";

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }


  @GetMapping("/courseList")
  public List<StudentsCourses> getCourseList() {
    return service.searchCourseList();

  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }
//難しい箇所👆

//上のメソッド何してるか？
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    System.out.println(result);
    if (result.hasErrors()) {
      return "registerStudent";

    }
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }
    //生徒一覧に一件をformから追加する
    //ここに何か処理入る。
    //下のDetailもおかし？

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id, Model model) {
      StudentDetail studentDetail = service.searchStudent(id);
      //studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
      model.addAttribute("studentDetail", studentDetail);
      return "updaterStudent";
    }
//難しい箇所👆

//上のメソッド何してるか？
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {

      if (result.hasErrors()) {
        return "updateStudent";
      }
        service.updateStudent(studentDetail);
   System.out.println(studentDetail.getStudent().getName() + "さんが新規受講生として登録されました。");
    return "redirect:/studentList";
  }
  @GetMapping("/courseList/{studentId}")
  public List<StudentsCourses> getCourseList(@PathVariable Long studentId) {
    return service.searchCourseList(studentId);
  }
}

