package project.springboot.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class EcomApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcomApplication.class, args);

		System.out.println("hello world");

	}

}
