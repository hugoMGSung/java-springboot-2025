package com.hugo83.spring05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring05Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring05Application.class, args);

		// 상속
		Dog ppoppy = new Dog();
		ppoppy.setName("뽀삐");
		ppoppy.cry();
		// ppoppy.age = -19;
		ppoppy.setAge(450); // 450살 입력
		System.out.println("뽀삐의 나이는 " + ppoppy.getAge() + "살");

		Cat kitty = new Cat();
		kitty.setName("키티");
		kitty.cry();	
		// kitty.age = 3;
		kitty.setAge(12);
		System.out.println("키티의 나이는 " + kitty.getAge() + "살");


		Dog choonsam = new Dog();		
		choonsam.setName("춘삼");
		choonsam.cry();
		System.out.println(choonsam.getClass().getSimpleName()); // Object 클래스 메서드를 사용가능
	}
}
