package raisetech.student.management2.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.data.Course;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

@RestController
public class StudentController {
  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }


  @GetMapping("/studentList")
  public List<StudentDetail> gethStudentList() {
    List<Student> students = service.searchStudentList();
    List<Course> courses = service.searchCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : students) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<Course> convertStudentCourses = new ArrayList<>();
//下の行コーディングsが怪しい
      for (Course course : courses) {
        if (student.getId().equals(course.getStudentId())) {
          convertStudentCourses.add(course);
        }
      }
      //下何やってるって？
      studentDetail.setCourse(convertStudentCourses);
      studentDetails.add(studentDetail);
    }

    return studentDetails  ;

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }

  @GetMapping("/courseList")
  public List<Course> getCourseList() {
    return service.searchCourseList();

  }

}
