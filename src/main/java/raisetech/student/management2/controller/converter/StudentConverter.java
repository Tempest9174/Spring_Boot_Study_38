package raisetech.student.management2.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management2.data.StudentsCourses;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;

/**
 * 受講生情報と受講生コース情報を受講生詳細に変換するコンバーター、もしくはその逆を行う
 */
@Component
public class StudentConverter {


  /**
   * 受講生に紐づく受講生コース情報をマッピングする
   * 受講生コース所法は受講生に対し複数存在するのでループを回して受講生詳細情報を組み立てる
   * @param studentList 受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentsCourses> studentCourseList) {
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
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);


      List<StudentsCourses> convertStudentCourseList = studentCourseList.stream()
          .filter(course -> student.getId().equals(course.getStudentId()))
          .collect(Collectors.toList());


      studentDetail.setStudentsCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}
