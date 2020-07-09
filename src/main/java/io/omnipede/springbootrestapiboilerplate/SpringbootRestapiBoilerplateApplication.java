package io.omnipede.springbootrestapiboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO: @RequestBody 자료형 체크 고안하기 <-
 * TODO: logging 추가, -> AOP 사용하기
 * TODO: properties 관리방법 고안하기
 * TODO: error response 에 errors 필드 추가하기
 * @see "https://cheese10yun.github.io/ConstraintValidator/"
 * TODO: DB 추가하기 - @Transactional 어노테이션 사용하기
 * TODO: clean architecture 구현하기
 * TODO: 필요없는 dependency 삭제하기, pom.xml jdk 버전 확인하기
 * TODO: response 를 domain 으로 옮기기
 * TODO: admin 페이지 설정해보기 "https://velog.io/@max9106/Spring-Boot-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%EC%9A%B4%EC%98%81"
 */
@SpringBootApplication
public class SpringbootRestapiBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestapiBoilerplateApplication.class, args);
	}

}
