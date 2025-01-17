package raisetech.student.management2.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.data.Student;


/**
 * 受講生情報を扱うレポジトリ
 * 全権検索や単一検索
 * 受講生テーブルと受講生コース情報テーブルと紐づくレポジトリ
 */
@Mapper
public interface StudentRepository {


  /**
   * 受講生の全件検索
   * @return 受講生一覧(全件)
   */
  List<Student> search();

  /**
   * 受講生の単一検索
   * @param  id 受講生ID
   * @return 受講生一覧()
   */
  Student searchStudent(String id);


  /**
   * 受講生コース情報の全件検索
   *
   * @return 受講生コースの一覧（全件）
   */
  List<StudentsCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentsCourse> searchStudentCourse(String studentId);

  //@Select("SELECT * FROM students_courses where student_id = #{studentId}")
  //List<StudentsCourse> searchStudentCourses(String studentId);
//上と競合するのでコメントアウト

  /**
   * 受講生を新規登録します。
   * IDに関しては自動採番
   * @param student 受講生
   */

  //新規受講生を追加するSQLを発行
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。
   * IDに関しては自動採番
   * @param studentsCourse 受講生コース情報
   */

  void registerStudentCourse(StudentsCourse studentsCourse);


  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */

  //更新SQLを発行
  void updateStudent(Student student);



  /**
      * 受講生コース情報のコース名を更新します。
      *
      * @param studentCourse 受講生コース情報
   */

  void updateStudentCourse(StudentsCourse studentCourse);
}
