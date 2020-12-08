package io.omnipede.springbootrestapiboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * TODO: @RequestBody json 자료형 확인하는 방법 고안하기
 * TODO: clean architecture 구현하기
 * TODO: gradle 공부하기
 * TODO: gradle multi-module
 */
@SpringBootApplication
@ComponentScan(basePackages = {
		"io.omnipede"
})
@EntityScan(basePackages = {
		"io.omnipede.springbootrestapiboilerplate.domain.purchase.entity",
		"io.omnipede.dataprovider.jpa.broadbandaccessdevice"
})
@EnableJpaRepositories(basePackages = {
		"io.omnipede.springbootrestapiboilerplate.domain.purchase.repository",
		"io.omnipede.dataprovider.jpa.broadbandaccessdevice"
})

public class SpringbootRestapiBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestapiBoilerplateApplication.class, args);
	}
}
