package com.pknu.backboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.pknu.backboard.entity")
public class BackboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackboardApplication.class, args);
	}

}
