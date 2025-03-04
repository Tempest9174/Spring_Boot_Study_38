package raisetech.student.management2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication

public class Application {


	public static void main(String[] args) {
//変更し各層へ移動
		SpringApplication.run(Application.class, args);
	}
}



	//@GetMapping("/studentList")
	//public List<Student> getStudentList() {
	//	return repository.search();
		//変数でなくStudent studentなのか？
		//表示
		//return student.getName() + " " + student.getAge() + "歳";


	//@GetMapping("/courseList")
	//public List<StudentsCourse> getCourseList() {
	//	return repository.searchCourses();


