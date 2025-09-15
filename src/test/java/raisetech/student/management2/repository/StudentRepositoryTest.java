package raisetech.student.management2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import raisetech.student.management2.data.Student;

@MybatisTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索ができる事() {

    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(10);

  }


  @Test
  void 受講生の登録ができること() {
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(11);
  }


  @Test
  void 受講生の登録が正しく完了している事() {
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    //登録した受講生が正しく元のまま登録されているか
    Student registeredStudent = actual.get(actual.size() - 1);
    assertThat(registeredStudent.getName()).isEqualTo("山田太郎");
    assertThat(registeredStudent.getKanaName()).isEqualTo("ヤマダタロウ");
    assertThat(registeredStudent.getNickName()).isEqualTo("やまちゃん");
    assertThat(registeredStudent.getEmail()).isEqualTo("test@gmail.com");
    assertThat(registeredStudent.getArea()).isEqualTo("東京");
    assertThat(registeredStudent.getAge()).isEqualTo(20);
    assertThat(registeredStudent.getSex()).isEqualTo("男性");
    assertThat(registeredStudent.isDeleted()).isEqualTo(false);
  }
  @Test
  void 受講生のIDで単一検索ができる事(){
    Student student = new Student();
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickName("やまちゃん");
    student.setEmail("test@gmail.com");
    student.setArea("東京");
    student.setAge(20);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    Student actual = sut.searchStudent("1");
    assertThat(actual.getName()).isEqualTo("山田太郎");
  }
}