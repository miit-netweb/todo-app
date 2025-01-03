package project.springboot.todo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodoApplication {

	public static void main(String[] args) {

		SpringApplication.run(TodoApplication.class, args);

		System.out.println("hello world");

	}

}
