package raisetech.student.management2.service;

import static java.time.LocalDate.now;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management2.controller.converter.StudentConverter;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.data.Student;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス
 * 検索や・登録更新処理を行う
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;


  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.converter = converter;

    this.repository = repository;
  }

  /**
   * 受講生詳細の一覧検索
   *  *全件検索の為、条件指定は行いません。
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
 //   レッスン24：return repository.search().stream()
    List<Student> studentList = repository.search();
    //.filter(student -> student.getAge() > 30).collect(Collectors.toList());
    List<StudentsCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得し設定
   * @param id 受講生ID
   * @return 受講生
   */
  public  StudentDetail searchStudent(String id){

    Student student = repository.searchStudent(id);
    //if (student == null) {
      //throw new StudentNotFoundException("指定されたIDの学生が見つかりません: " + id);
    //}
    List<StudentsCourse> studentCourse = repository.searchStudentCourse(student.getId());
      return new StudentDetail(student, studentCourse);
  }
//サーチ処理を行う（コントローラ）
 // public StudentsCourse courseViewList(){
 //   return repository.overViewCourse();
 // }
  /**
   * 受講生コース検索を行う
   * 全件検索を行うため条件指定は行わない
   *
   * @return 受講生コース一覧
   */
  public List<StudentsCourse> searchCourseList() {
    return repository.searchStudentCourseList();

  }

  /**
   * 受講生詳細の登録を行う
   * 受講生とコース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値や日付を設定（コース開始日）
   * @param studentDetail 受講生詳細
   *
   * @return 登録情報を付与した受講生詳細
   */



  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail){
    //準備
    Student student = studentDetail.getStudent();
    //やりたいこと
    repository.registerStudent(student);
    studentDetail.getStudentsCourseList().forEach(studentsCourse -> {
      initStudentCourse(studentsCourse, student);
      repository.registerStudentCourse(studentsCourse);
    });

    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期化を行う
   * @param studentsCourse 受講生コース情報
   * @param student 受講生
   */
  private void initStudentCourse(StudentsCourse studentsCourse, Student student) {
    studentsCourse.setStudentId(student.getId());
    Date now = Date.valueOf(now());


    studentsCourse.setCourseStartAt(now);
    studentsCourse.setCourseEndAt(Date.valueOf(now().plusYears(1)));
  }
//TODO: コース情報の登録も行う

  /**
   * 受講生詳細の更新を行う
   * 受講生情報と受講生コース情報を個別に更新する
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    //学生の登録情報を更新処理する
    //  studentsCourses.setStudentId(studentDetail.getStudent().getId());
    //駄目な実装
    studentDetail.getStudentsCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));


    }
  }


