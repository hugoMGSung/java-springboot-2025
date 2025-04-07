package com.hugo83.spring02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring02Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Spring02Application.class, args);

		// 파일쓰기, FileOutputStream
		FileOutputStream output = new FileOutputStream("C:/temp/test2.txt");

		for (int i=0; i<10; i++) {
			StringBuilder sb = new StringBuilder((i+1) + "번째 줄입니다.\n");
			output.write(sb.toString().getBytes(Charset.defaultCharset()));
		}
		output.close();
		System.out.println("FileOutputStream 파일 생성 완료!");

		// FileWriter
		FileWriter fw = new FileWriter("C:/temp/fwtext.txt");
		
		for (int i=0; i<10; i++) {
			String str = (i+1) + "번째 줄입니다.\n";
			fw.write(str);
		}
		fw.close();
		System.out.println("FileWriter 파일 생성 완료!");

		// PrintWriter - \n을 사용하지 않는 방식
		PrintWriter pw = new PrintWriter("C:/temp/pwtext.txt");

		for (int i=0; i<10; i++) {
			String str = (i+1) + "번째 줄입니다."; // \r\n이 필요없음
			pw.println(str);
		}
		pw.close();
		System.out.println("PrintWriter 파일 생성 완료!");

		// FileWriter 추가모드 사용
		FileWriter fw2 = new FileWriter("C:/temp/fwtext.txt", true);

		for (int i=100; i<110; i++) {
			String str = (i+1) + "번째 추가줄입니다.\n";
			fw2.write(str);
		}
		fw2.close();
		System.out.println("FileWriter 파일 추가생성 완료!");

		// PrintWriter는 FileWriter를 활요해서 추가모드 사용
		PrintWriter pw2 = new PrintWriter(new FileWriter("C:/temp/pwtext.txt", true));

		for (int i=200; i<210; i++) {
			String str = (i+1) + "번째 추가줄입니다.";
			pw2.println(str);
		}
		pw2.close();
		System.out.println("PrintWriter 파일 추가생성 완료!");

		// FileInputStream - 한번에 전부를 읽어올때. 대용량문서는 읽다가 예외발생
		byte[] b = new byte[2048];
		FileInputStream input = new FileInputStream("C:/temp/fwtext.txt");
		input.read(b);  // 글자를 전부 읽어서 바이트배열에 할당
		System.out.println(new String(b)); // 바이트배열을 문자열로 변경
		input.close();

		System.out.println("FileInputStream 으로 읽기 완료!!!!");

		// BufferedReader - 한줄씩 읽어올때.
		BufferedReader br = new BufferedReader(new FileReader("C:/temp/pwtext.txt"));
		while (true) {
			String line = br.readLine();
			if (line == null) break;  // 더이상 읽을 라인이 없으면 탈출.
			System.out.println(line);
		}
		br.close();
		System.out.println("BufferedReader 로 읽기 완료!!!!");
	}
}
