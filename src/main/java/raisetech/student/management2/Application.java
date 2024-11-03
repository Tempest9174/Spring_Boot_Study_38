package raisetech.student.management2;

import ch.qos.logback.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		//変数でなくStudent studentなのか？
		//表示
			return student.getName() + " " + student.getAge() + "歳";
	}
	//Student student = new Student();
	//return name + " " + age + "歳";

	@PostMapping("/student")
	public void registerStudent(String name, int age) {
		repository.registerStudent(name, age);
		//挿入
	}

	@PatchMapping("/student")
	public void updateStudentName(String name, int age) {
		repository.updateStudent(name,age);

	}
	@DeleteMapping("/student")
	public void deleteStudent(String name) {
		repository.deleteStudent(name);
	}
}
