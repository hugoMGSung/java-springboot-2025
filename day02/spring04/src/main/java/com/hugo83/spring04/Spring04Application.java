package com.hugo83.spring04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring04Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring04Application.class, args);

		// 클래스 사용
		NewCar peugeot = new NewCar("206cc", "peugeot", 2016);		

		NewCar avante = new NewCar();
		avante.company = "현대자동차";
		avante.color = "blue";
		avante.fuel_type = "휘발유";
		avante.model_name = "avante s16";
		avante.release_year = 2022;

		peugeot.doAccelerate();
		avante.doAccelerate();
		peugeot.turnLeft();
		avante.turnRight();
		peugeot.doBreak();
		avante.doBreak();
		avante.changeGear();
		avante.doAccelerate();

		System.out.println(peugeot);
		System.out.println(avante);
	}
}
