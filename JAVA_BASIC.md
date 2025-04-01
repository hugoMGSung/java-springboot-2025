## Java 기초문법

### 기본
- 자바 소스코드 구조

    ```java
    // 한줄 주석.
    /* 여러줄 
       주석. */
    package com.hugo83.spring01; // 자신 프로젝트의 패키지명(폴더)

    import org.springframework.boot.SpringApplication;  // 라이브러리, 클래스 가져오기
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication  // 자바프로젝트 실행을 도와주는 어노테이션
    public class Spring01Application { // 자신 클래스명

        // 엔트리포인트(프로젝트당 하나만 존재)
        public static void main(String[] args) { // 함수, 클래스, 조건문, 반복문시작이 중괄호({)
            SpringApplication.run(Spring01Application.class, args); // 한줄이 끝나면 반드시 ;

            System.out.println("Hello, Spring Boot!"); // 콘솔출력, 문자열을 항상 "
        }
    }
    ```

- 자바특징
    - `간결`하면서 강력한 객체지향 언어
    - `플랫폼 독립적`, OS에 영향을 거의 받지 않음
    - 라이브러리가 아주 다양, `생산성` 향상
    - GUI 프로그램 개발에는 부적합(Swing, JavaFX로 가능)
    - 정밀하게 HW를 제어하는 프로그램에도 부적합

- `JVM`위에서 동작
    - Java Virtual Machine : 도커 컨테이너와 비슷한 개념
    - sourcecode.java -> javac(java compiler) -> bytecode.class -> java로 실행

- 활용분야
    - 웹에 특화. 인터넷 포털, 인터넷 뱅킹, 쇼핑몰, 기업시스템
    - 초기 안드로이드 개발 대표 언어였음. 현재는 Kotlin으로 변경

### 기초문법

#### 변수
- 변수 선언방법

    ```java
    [접근제어자] 타입 변수명;
    ```
    - 접근제어자 - 변수의 접근범위를 지정하는 키워드(public, default, protected, private)
    - 타입 - 자료형. 자바는 명시적으로 타입 지정해야 함
    - 변수명 - 일반적인 변수명 지정법과 동일

    ```java
    int account = 10000000;     // 정수형(Primitive type)
    private String sayhi = "Hello!";    /// 문자열형(Class type)
    MyClass inst = new MyClass();     // 사용자정의 클래스형
    ```

- 변수종류
    - 지역변수, 전역변수, 매개변수, 멤버변수, 인스턴스변수...

#### 자료형
- 원시자료형(Primitive type)

    |구분|자료형|크기|설명|
    |:---:|:---:|:---|:---|
    |정수형|byte|1byte, -128~127|가장 작은단위, 8bit |
    |      |char|2bytes, 0~65535|unsigned자료형, 문자표현용 |
    |      |short|2bytes, -32768~32767|signed자료형, 작은데이터처리용 |
    |      |int|4bytes, -2147백만~2147백만|기본자료형  |
    |      |long|8bytes, -922경~922경|큰정수자료형, 숫자뒤에 L표시 |
    |실수형|float|4bytes, 1.4E-45~3.40E38|실수형, 숫자뒤에 F표시 |
    |      |double|8bytes, 4.9E-324~1.79E308|실수형기본자료형 |
    |논리형|boolean|1byte, true또는false |논리형, 참/거짓표현용 |

    ```java
    int number; 
    char ch_first = 'a';  
    long longnum = 2345678L;
    float fnum = 3.141592F;
    boolean isTrue = false;
    ```

#### 연산자
- 연산자는 거의 대부분의 언어에서 동일하게 사용
    - 사칙연산 : +, -, * , /, %
    - 대입연산 : =, +=, -=, *=, /=, %=, ++(a++, a = a + 1 동일), -- 
    - 비트연산 : &, |, ^, ~, >>, <<
    - 논리연산 : &&, ||, !
    - 관계연산 : ==, >, >=, <, <=, !=


#### 흐름제어
- 조건(분기)문 : if문, switch문
    - if문
    
    ```java
    if (조건식) {
        // if문 안으로 들어와서
        실행코드 블럭;
    } else if (조건식) {
        실행코드 블럭;
    } else {
        // 어디에도 참이 되지 않으면...
        실행코드 블럭;
    }
    ```

    - switch(-case)문 - 조건값에 따라 여러 분기로 나눌때 좋음. if문으로 대체 가능

    ```java
    switch (입력변수) {
        case 조건값1:
            실행코드 블럭;
            break;
        case 조건값2:
            실행코드 블럭;
        case 조건값3:
            실행코드 블럭;
            break;  // 조건값2에서 시작하면 조건값3까지 모두 수행
        default:
            기본실행코드 블럭;
            break; // case나 default에 되도록이면 빼지말것
    }
    ```

    - 3항 연산 - 간단하게 구현할 조건문 if-else 대신 사용. 실행코드 블럭이 한줄일때

    ```java
    String result = (isLogin) ? "로그인성공!" : "로그인실패";

    String result;
    if (isLogin) {
        result = "로그인성공!";    
    } else {
        result = "로그인실패";
    }
    ``` 


- 반복문 : for문, while문, do-while문

    - for문 - 대표적인 반복문

    ```java
    // 일반적인 반복문
    for (초기값; 조건식; 증감식) {
        실행코드 블럭;
    }

    // 배열, 컬렉션을 처리하는 반복문
    for (변수 : 컬렉션데이터) {
        실행코드 블럭;
    }
    ```

    - while문 - 조건식이 참인동안 계속 반복

    ```java    
    while (조건식) {
        실행코드 블럭;
    }
    ```

    - do-while문 - 우선 실행코드를 한번 수행 후 조건을 검사후 반복

    ```java    
    do {
        실행코드 블럭;
    } while(조건식);

    ```

- 반복제어 키워드 : break, continue
    - for문, while문 내에서 사용
    - break - 조건에 맞아 반복문을 탈출할 때
    - continue - 특수 조건만 비켜서 반복문을 계속할 때