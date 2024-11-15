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
    //生徒リストを取得
    List<Course> courses = service.searchCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    //生徒詳細リストを生成
    for (Student student : students) {
      StudentDetail studentDetail = new StudentDetail();
      //生徒詳細を生成
      studentDetail.setStudent(student);
      //生徒詳細に生徒をセット
      List<Course> convertStudentCourses = new ArrayList<>();
      //生徒コースリストを生成
//下の行コーディングsが怪しい
      for (Course course : courses) {
        if (student.getId().equals(course.getStudentId())) {
          //生徒IDとコースの生徒IDが一致したら
          convertStudentCourses.add(course);

        }
      }
      //下何やってるって？
      studentDetail.setCourse(convertStudentCourses);
      //生徒詳細に生徒コースをセット
      studentDetails.add(studentDetail);
      //生徒詳細リストに生徒詳細を追加
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
