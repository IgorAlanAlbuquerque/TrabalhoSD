package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.model"})
public class ApplicationServidor {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServidor.class, args);
	}

}
