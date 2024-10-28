package raisetech.student.management2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {
	private  String name = "yamada";
	private int age = 20;

	public static void main(String[] args) {
//変更
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/name")
	public String getName() {
		return name;
	}
	@PostMapping("/name")
	public  void setName(String name) {
		this.name = name;
	}
}
