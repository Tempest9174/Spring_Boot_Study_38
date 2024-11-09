package raisetech.student.management2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.data.Course;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.service.StudentService;

@RestController
public class StudentController {
  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }


  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.serchStudentList();
    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }

  @GetMapping("/courseList")
  public List<Course> getCourseList() {
    return service.searchCourseList();

  }

}
