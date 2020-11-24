package io.omnipede.springbootrestapiboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Filter;

/**
 * TODO: @RequestBody json 자료형 확인하는 방법 고안하기
 * TODO: clean architecture 구현하기
 * TODO: gradle 공부하기
 * TODO: gradle multi-module
 */
@SpringBootApplication
public class SpringbootRestapiBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestapiBoilerplateApplication.class, args);
	}

	/**
	 * UTF-8 인코딩을 위해 등록
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	}

	/**
	 * UTF-8 인코딩을 위해 등록
	 */
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
