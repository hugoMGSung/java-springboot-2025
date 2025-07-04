package com.pknu.backboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackboardApplication.class, args);
	}

}
