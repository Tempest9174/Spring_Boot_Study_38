package raisetech.student.management2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.controller.converter.StudentConverter;
import raisetech.student.management2.data.Course;
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
    List<Course> courses = service.searchCourseList();
    model.addAttribute("studentList",converter.convertStudentDetails(students, courses));//コースリストを取得

    return "studentList";

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }


  @GetMapping("/courseList")
  public List<Course> getCourseList() {
    return service.searchCourseList();

  }

}
