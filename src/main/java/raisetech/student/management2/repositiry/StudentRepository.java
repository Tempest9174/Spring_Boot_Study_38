package raisetech.student.management2.repositiry;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management2.data.StudentsCourses;
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
  @Select("SELECT * FROM students")
  //@Select("SELECT * FROM students where isDeleted = false")
  List<Student> search();

  /**
   * 受講生の単一検索
   * @param  id 受講生ID
   * @return 受講生一覧()
   */
  @Select("SELECT * FROM students where id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生コース情報の全件検索
   *
   * @return 受講生コースの一覧
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentCoursesList();

  /**
   *
   *
   * @param studentId
   * @return
   */
  @Select("SELECT * FROM students_courses where student_id = #{studentId}")
  List<StudentsCourses> searchStudentCourses(String studentId);

  //@Select("SELECT * FROM students_courses where student_id = #{studentId}")
  //List<StudentsCourses> searchStudentCourses(String studentId);
//上と競合するのでコメントアウト

  //新規受講生を追加するSQLを発行
  @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, isDeleted) values( #{name},  #{kanaName}, #{nickName},#{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);



  @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at)"
  + "values(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  //更新SQLを発行
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickName}, email = #{email}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
  //@Optionsいらない
  void updateStudent(Student student);

  @Update("UPDATE students_courses set course_name = #{courseName} where id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);




}
