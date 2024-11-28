package raisetech.student.management2.service;

import static java.time.LocalDate.now;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setCourseStartAt(Date.valueOf(now()));
      studentsCourse.setCourseEndAt(Date.valueOf(now().plusYears(1)));

      repository.registerStudentsCourses(studentsCourse);
    }
  }
//TODO: コース情報の登録も行う
}

