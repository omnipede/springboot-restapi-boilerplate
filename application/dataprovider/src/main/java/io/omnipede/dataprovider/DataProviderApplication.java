package io.omnipede.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JPA repository interface 를 테스트하기 위해 임시로 spring boot application 을 정의함
 */
@SpringBootApplication
public class DataProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProviderApplication.class, args);
    }
}
