package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
<<<<<<< HEAD
		int fff=2;
=======
		int a=2;
>>>>>>> branch 'master' of https://github.com/FnuJava/spring-boot.git
		System.out.println(1);
	}
	
	
	
}
