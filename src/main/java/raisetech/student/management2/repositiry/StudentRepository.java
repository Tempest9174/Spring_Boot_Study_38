package raisetech.student.management2.repositiry;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management2.data.Course;
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
  List<Course> searchCourses();


}
