package raisetech.student.management2.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management2.data.StudentsCourses;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
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

  public List<StudentsCourses> searchCourseList() {
    return repository.searchCourses();

  }
//  public void  addStudentList( Student student) {
//
  //   repository.addStudent(student);
  //}
  @Transactional
  public void registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
  }
//TODO: コース情報の登録も行う
}

