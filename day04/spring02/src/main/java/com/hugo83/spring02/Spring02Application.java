package com.hugo83.spring02;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring02Application {

	public static void main(String[] args) {
		int num = 10;
		float fnum = 3.14f;
		double dnum = 345.67;
		long lnum = 34865432783L;

		Integer num2 = 10;
		Float fnum2 = 3.15f;
		Double dnum2 = 32423847.98273;
		Long lnum2 = 238743278l;

		SpringApplication.run(Spring02Application.class, args);

		// 제네릭 문자열사용
		Storage<String> storage1 = new Storage<String>();
		storage1.setItem("ionic");
		System.out.println(storage1.getItem());

		// 제네릭 정수사용
		Storage<Float> storage2 = new Storage<>();
		storage2.setItem(3.141592F);
		System.out.println(storage2.getItem());

		// 리턴타입 및 인자가 모두 제네릭일때 처리
		Spring02Application app = new Spring02Application();
		System.out.println(app.convert(storage1));
		System.out.println(app.convert(storage2));
	}

	// storage에 들어은 아이템을 문자로 분리해서 리스트에 담는 기능
	public <T> List<Character> convert(Storage<T> storage) {
		ArrayList<Character> list = new ArrayList<>();
		
		String s = String.valueOf(storage.getItem());
		for (int i = 0; i < s.length(); i++) {
			list.add(s.charAt(i));
		}

		return list;
	}

	// 제네릭타입으로 어떤 타입이 들어올지 모를때 쓰는 와일드카드
	public void print(Storage<? extends Storage> st) {
		// 내용 생략.
	}
}
