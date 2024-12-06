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
 */
@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students where id = #{id}")
  Student searchStudent(String id);


  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentCoursesList();
//}

  @Select("SELECT * FROM students_courses where student_id = #{studentId}")
  List<StudentsCourses> searchStudentCourses(String studentId);

  //@Select("SELECT * FROM students_courses where student_id = #{studentId}")
  //List<StudentsCourses> searchStudentCourses(String studentId);
//上と競合するのでコメントアウト

  //新規受講生を追加するSQLを発行
  @Insert("INSERT INTO students(name, kana_name,nickname,email, area, age, sex, remark, isDeleted) values( #{name}, #{nickName}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
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
