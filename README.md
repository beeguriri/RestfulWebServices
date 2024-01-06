# ✨ RESTful Web Services 개발
- 관련 강의 : [Spring Boot 3.x 를 이용한 RESTful Web Services 개발](https://www.inflearn.com/course/spring-boot-restful-web-services/dashboard)

## 개발 환경
- SpringBoot version `3.2.1`
- Java version `17`

## DispatcherServlet
![](/images/dispatcher_servlet.png)
- `dispatcher-servlet` : 클라이언트에게 요청을 받아 응답까지의 MVC 처리과정 통제
- `handler-mapping` : 클라이언트의 요청을 어느 컨트롤러가 처리할 지 결정
- `handler-adapter` : 컨트롤러 직접 호출
- `view-resolver` : 컨트롤러의 처리 결과를 담을 view 결정
- 요청/응답 순서
  - ① 클라이언트는 URL을 통해 요청 보냄
  - ② `dispatcher-servlet`은 `handler-mapping`을 통해 컨트롤러를 찾음
  - ③ `dispatcher-servlet`은 `handler-adapter`에게 요청 전달을 맡김
  - ④ `handler-adapter`는 해당 컨트롤러에 요청 전달
  - ⑤ 컨트롤러는 비즈니스 로직 처리 후 뷰 정보 반환 
  - ⑥ `dispatcher-servlet`은 `view-resolver`를 통해 반환 할 뷰 찾음
  - ⑦ `dispatcher-servlet`이 view 반환
  - ⑧ 클라이언트에 html 응답

## SpringBoot API 사용하기
### ⭐HATEOAS
- Hypermedia As the Engine Of Application State
- 현재 리소스와 연관 된 자원 상태 정보를 제공
- 클라이언트가 서버로부터 어떤 요청을 할 때, 요청에 필요한 URI를 응답에 포함시켜 반환

```json
{
    "id": 1,
    "name": "Alice",
    "joinDate": "2024-01-06T03:40:15.425+00:00",
    "_links": {
        "all-users": {
            "href": "http://localhost:8088/users"
        }
    }
}
```
### ⭐Swagger를 이용한 API 문서화
```java
//swagger 사용을 위한 dependency 추가
//확인: http://localhost:8088/swagger-ui/index.html#/
implementation 'org.springframework.boot:spring-boot-starter-parent:3.1.2'
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'
```
### ⭐Spring Actuator를 이용한 모니터링
```java
//스프링부트 액추에이터 사용
//확인: http://localhost:8088/actuator 의 end point 확인 후 추가
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```
### ⭐Hal Explorer를 이용한 API 테스트
```java
//hal browser 사용을 위한 dependency 추가
//확인: http://localhost:8088 에서 end point 확인 후 추가
implementation 'org.springframework.data:spring-data-rest-hal-explorer'
```
![](/images/hal_explorer.png)
### ⭐Spring Security를 이용한 인증 처리
```java
//spring-security 사용을 위한 dependency 추가
implementation 'org.springframework.boot:spring-boot-starter-security'
```

## RESTful API 설계 가이드
![](/images/restful.png)
### ⭐Level 0
- 리소스가 어떤 작업을 해야하는지 uri에 명시

### ⭐Level 1
- 사용자의 요청을 단순히 get, post로만 처리하고 있음
- 모든 반환 코드를 200 ok 로 하고 있음

### ⭐Level 2
- level1 + HTTP Methods
- 적절한 메소드에 맞게 설계
  - 리소스의 상태가 변경할 수 없는 경우 get
  - 새로운 리소스 추가 post
  - 수정할 경우 put, fetch
  - 삭제할 경우 delete 

### ⭐Level 3
- level2 + HATEOAS
- 다음 작업으로 어떤 작업을 할 수 있는지 알려줌
- 클라이언트에게 최소한의 엔드포인트 접속으로 다음 할 수 있는 작업을 알려줌

## ⭐설계 가이드
- 적절한 Request methods 사용
- 적절한 Response Status 사용
- 리소스를 명사 형태로 사용
- 리소스의 단수/복수 구분해서 사용
  - /users
  - /user/1
- 일관된 엔드포인트 사용
  - PUT /gists/{id}/star
  - DELETE /gists/{id}/star
