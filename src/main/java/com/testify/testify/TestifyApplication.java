package com.testify.testify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.testify.testify")
public class TestifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestifyApplication.class, args);
	}

}
