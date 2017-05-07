package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		int abc3=5;
		int abc6=2;		
		System.out.println(1);
	}
	
	
	
}
