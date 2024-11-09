package raisetech.student.management2;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private StudentRepository repository;




	private String name = "yamada";
	private int age = 20;

	public static void main(String[] args) {
//変更
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.search();
		//変数でなくStudent studentなのか？
		//表示
		//return student.getName() + " " + student.getAge() + "歳";
	}
	//Student student = new Student();
	//return name + " " + age + "歳";

}
