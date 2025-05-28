package raisetech.student.management2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
		info = @Info(
				title = "受講生管理システム",
				version = "1.0.0"
		)
)

@SpringBootApplication

public class Application {


	public static void main(String[] args) {
//変更し各層へ移動
		SpringApplication.run(Application.class, args);
	}
}


