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

    - [Java문법실습](./day01/spring01/src/main/java/com/hugo83/spring01/Spring01Application.java)

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
    - [Java문법실습](./day01/spring02/src/main/java/com/hugo83/spring02/Spring02Application.java)

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

    - [Java문법실습](./day01/spring03/src/main/java/com/hugo83/spring03/Spring03Application.java)

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

    - [Java문법실습](./day02/spring01/src/main/java/com/hugo83/spring01/Spring01Application.java)

- 반복제어 키워드 : break, continue
    - for문, while문 내에서 사용
    - break - 조건에 맞아 반복문을 탈출할 때
    - continue - 특수 조건만 비켜서 반복문을 계속할 때

#### 배열
- 여러 데이터가 필요할 경우 하나의 변수로 처리 가능
- 여러개의 데이터를 순차적으로 저장할 때 인덱스가 0부터 시작
- 데이터를 순차적으로 접근하기 때문에 위치를 모르면 효율이 좋지 않음
- Java의 배열은 모두 동일한 자료형이여야 함
- 중간의 데이터를 추가하거나 삭제하려면 기존 데이터를 모두 이동해야 함

    ```java
    타입 변수명[] = {데이터 배열값, ...}; // 데이터 갯수에 따라 배열 사이즈가 결정
    타입[] 변수명 = {데이터 배열값, ...};
    타입[] 변수명 = new 타입[5];  // 아무값 없이 초기화
    
    변수명[1]; // 배열 1번 인덱스에 위치
    변수명[1] = 값;  // 값 할당
    System.out.println(변수명[1]); // 값 사용
    ```

- 배열에 사용할 메서드 - Arrays 객체를 사용
    - sort() : 배열 정렬
    - toString() : 배열 객체ID를 출력
    - asList() : 배열을 리스트로 변경(List)
    - copyOf() : 배열을 복사해서 새로운 배열로 반환
    - equals() : 두개 배열이 동일한지 여부 판별
    - binarySearch() : 배열내 요소를 찾아서 인덱스를 반환

- Java 컬렉션 프레임워크 - 배열을 좀더 기능적으로 강화한 객체
    - **List**, Stack, Vector, ArrayList, HashSet, HashMap(파이썬 딕셔너리), HashTable

- [Java문법실습](./day02/spring02/src/main/java/com/hugo83/spring02/Spring02Application.java)

#### 메서드
- 함수와 동일. 객체지향언어에서는 함수를 메서드로 사용

    ```java
    [접근제어자] 리턴타입 메서드명([파라미터...]) {
        메서드 코드 블럭;
    }
    ```

    - 메서드 오버로딩 - 동일한 메서드명에 파라미터가 다르게 선언
    - 가변 인자(파라미터) - 파라미터 개수가 동적일 때 사용

- **static**
    - 소스코드 실행후 클래스 인스턴스가 생성안됨
    - 이 상태에서 프로그램 엔트리포인트 메서드는 바로 실행되어야 함
    - static으로 하면 프로그램 실행 전부터 종료 직전까지 그냥 사용할 수 있는 메서드가 됨
    - static 메서드에서는 같은 클래스 내 일반 메서드는 호출 불가
    - static 메서드만 호출 가능
    - 다른 클래스는 상관없음

- **null**
    - 프로그래밍 상에 모든 곳에서 null이라고 사용
    - 파이썬만 None으로 사용

- [Java문법실습](./day02/spring03/src/main/java/com/hugo83/spring03/Spring03Application.java)

#### 객체/클래스
- 객체 개요
    - 현실세계를 반영하여 프로그래밍을 하는 기법
    - Object-Oriented Programming - OOP, 객체지향 프로그래밍
        - C++, Java, Python, C#, Go, Rust, Kotlin ... 
    - vs. Procedure-Oriented Programming - 절차적 프로그래밍
        - C, SQL, ...
    
- 클래스 개요
    - 객체를 정의하는 틀
    - 명사(멤버변수/속성)와 동사(멤버메서드)의 집합

    ```java
    class NewCar {
        // 명사부분(멤버변수/속성)
        String model_name;
        String company;
        String color;
        int release_year;
        String fuel_type;

        // 동사부분(메서드)
        public void doAccelerate() { // 가속
        }
        public void doBreak() { // 중지
        }
        public void changeGear() { // 기어변동
        }
        public void turnLeft() { // 좌회전
        }
        public void turnRight() {  // 우회전
        }

        // 생성자메서드(파이썬 __init__와 유사)
        NewCar() {
            model_name = "없음";
            company = "현대자동차";
        }
        NewCar(String model_name, String company, int release_year) {
            this.model_name = model_name;
            this.company = company;
            this.release_year = release_year;
        }
    }
    ```

- 인스턴스(instance) 
    - 객체를 만드는 클래스를 new로 새로 생성
    - 두개의 인스턴스가 하나를 가르킬 수 없음

    ```java
    NewCar avante = new NewCar();  // 기본생성자
    NewCar tesla = new NewCar("X3", "Tesla", 2021);  // 인자초기화 생성자

    tesla.turnRight(); //우회전
    // ...
    ```
- [Java문법실습](./day02/spring04/src/main/java/com/hugo83/spring04/Spring04Application.java)

#### 상속
- 개요
    - 객체지향에 중요한 의미의 개념
    - 부모클래스가 가지고 있는 속성, 메서드를 가져다 쓸 수 있는 기능
    - 예 - 동물 -> 포유류, 양서류, 파충류 등으로 분류

- 특징
    - 부모(슈퍼)클래스에서 정의된 변수와 메서드를 물려받는다
    - 부모클래스에서 물려받은 메서드를 수정할 수 있다(오버라이딩)
    - 새로운 변수와 메서드를 추가할 수 있다
    - 동일안 부모클래스를 상속받은 하위 자식클래스들은 타입이 호환된다

    ```java
    [접근제어자] class 클래스명 extends 부모클래스명 {
        // 부모클래스에 없는 멤버변수, 메서드 추가
        // 부모 메서드를 오버라이딩
    }
    ```
- [Java문법실습](./day02/spring05/src/main/java/com/hugo83/spring05/Spring05Application.java)

#### Getter/Setter
- 내일

#### Object 클래스
- 내일