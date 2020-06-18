package io.omnipede.springbootrestapiboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @see "https://cheese10yun.github.io/spring-guide-directory/"
 * @see "https://github.com/cheese10yun/spring-guide/blob/master/src/main/java/com/spring/guide/domain/member/application/MemberProfileService.java"
 * TODO: @RequestBody 타입 체크 고안하기 <-
 * TODO: logging 추가, -> AOP 사용하기
 * TODO: request ~ response filter 추가
 * TODO: error response 에 errors 필드 추가하기
 * TODO: 에러 케이스 테스트 클래스 추가
 * @see "https://cheese10yun.github.io/ConstraintValidator/"
 * TODO: DB 추가하기 - @Transactional 어노테이션 사용하기
 * TODO: clean architecture 구현하기
 * TODO: 필요없는 dependency 삭제하기, pom.xml jdk 버전 확인하기
 */
@SpringBootApplication
public class SpringbootRestapiBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestapiBoilerplateApplication.class, args);
	}

}
