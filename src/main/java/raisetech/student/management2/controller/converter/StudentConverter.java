package raisetech.student.management2.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management2.data.Course;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;

@Component
public class StudentConverter {
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<Course> courses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    //生徒詳細リストを生成
    //生徒詳細を生成
    //生徒詳細に生徒をセット
    //convert生徒コースリストを生成
    //下の行コーディングsが怪しい
    //生徒IDとコースの生徒IDが一致したら
    //生徒コースリストにコースを追加??
    //下何やってるって？
    //生徒詳細にconvert生徒コースリストをセット
    //生徒詳細リストに生徒詳細を追加
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);


      List<Course> convertStudentCourses = courses.stream()
          .filter(course -> student.getId().equals(course.getStudentId()))
          .collect(Collectors.toList());


      studentDetail.setCourse(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}
