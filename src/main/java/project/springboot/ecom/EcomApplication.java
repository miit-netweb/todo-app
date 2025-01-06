package project.springboot.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EcomApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcomApplication.class, args);

		System.out.println("hello world");

	}

}
