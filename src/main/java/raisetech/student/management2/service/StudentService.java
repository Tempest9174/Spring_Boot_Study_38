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

  public List<Student> searchStudentList() {
 //   レッスン24：return repository.search().stream()
 //           .filter(student -> student.getAge() > 30).collect(Collectors.toList());
    return repository.search();
//ここで何か処理を行う
  }

  public List<Course> searchCourseList() {
    return repository.searchCourses();

  }
  public void  addStudentList( Student student) {

     repository.addStudent(student);
  }
}
