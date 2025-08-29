package raisetech.student.management2.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.data.CourseResponse;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.data.StudentRequest;
import raisetech.student.management2.data.StudentResponse;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.service.StudentService;

/**
 * JavaScript フロントエンドと連携するための REST API コントローラー
 */
@RestController
@RequestMapping("/api")
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/students")
  public List<StudentResponse> listStudents() {
    return service.searchStudentList().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @GetMapping("/students/{id}")
  public StudentResponse getStudent(@PathVariable String id) {
    return toResponse(service.searchStudent(id));
  }

  @PostMapping("/students")
  public StudentResponse createStudent(@RequestBody StudentRequest request) {
    StudentDetail detail = fromRequest(request);
    StudentDetail saved = service.registerStudent(detail);
    return toResponse(saved);
  }

  @PutMapping("/students/{id}")
  public StudentResponse updateStudent(@PathVariable String id, @RequestBody StudentRequest request) {
    StudentDetail detail = fromRequest(request);
    detail.getStudent().setId(id);
    service.updateStudent(detail);
    return toResponse(service.searchStudent(id));
  }

  @GetMapping("/courses")
  public List<CourseResponse> listCourses() {
    List<StudentsCourse> all = service.searchCourseList();
    Map<String, Long> grouped =
        all.stream().collect(Collectors.groupingBy(StudentsCourse::getCourseName, Collectors.counting()));
    AtomicInteger seq = new AtomicInteger(1);
    return grouped.entrySet().stream()
        .map(e -> new CourseResponse(String.valueOf(seq.getAndIncrement()), e.getKey(), e.getValue().intValue()))
        .collect(Collectors.toList());
  }

  private StudentResponse toResponse(StudentDetail detail) {
    Student s = detail.getStudent();
    List<String> courses = detail.getStudentsCourseList().stream()
        .map(StudentsCourse::getCourseName)
        .collect(Collectors.toList());
    return new StudentResponse(
        s.getId(),
        s.getName(),
        s.getKanaName(),
        s.getNickName(),
        s.getEmail(),
        s.getArea(),
        s.getAge(),
        s.getSex(),
        s.getRemark(),
        s.isDeleted(),
        courses);
  }

  private StudentDetail fromRequest(StudentRequest req) {
    Student student = new Student();
    student.setName(req.getName());
    student.setKanaName(req.getKanaName());
    student.setNickName(req.getNickName());
    student.setEmail(req.getEmail());
    student.setArea(req.getArea());
    student.setAge(req.getAge());
    student.setSex(req.getSex());
    student.setRemark(req.getRemark());
    student.setDeleted(false);

    List<StudentsCourse> courses = (req.getCourses() == null) ? List.of() :
        req.getCourses().stream().map(name -> {
          StudentsCourse sc = new StudentsCourse();
          sc.setCourseName(name);
          return sc;
        }).collect(Collectors.toList());

    return new StudentDetail(student, courses);
  }
}