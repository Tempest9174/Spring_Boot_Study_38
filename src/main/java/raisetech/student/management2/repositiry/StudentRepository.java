package raisetech.student.management2.repositiry;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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

//}
//@Mapper
//public interface CourseRepository {
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourses();

  //新規受講生を追加するSQLを発行
  @Insert("INSERT INTO students(name, kana_name,nickname,email, area, age, sex, remark, isDeleted) values( #{name}, #{nickName}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

}
