package raisetech.student.management2.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management2.data.Course;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.repositiry.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> serchStudentList() {
      serchStudentList().stream().filter(e -> e.getAge() > 30).toList().forEach(System.out::println);
    return repository.search();
//ここで何か処理を行う
  }

  public List<Course> serchCourseList() {
    return repository.searchCourses();

  }
}
