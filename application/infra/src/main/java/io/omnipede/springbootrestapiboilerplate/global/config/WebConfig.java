package io.omnipede.springbootrestapiboilerplate.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * No handler found exception 을 처리하기 위해
 * application.yaml 파일에
 * spring:
 *  mvc:
 *   throw-exception-if-no-handler-found: true
 * resources:
 *  add-mappings: false
 * ~을 추가함.
 * 이로 인해 static 파일을 찾을 수 없는 문제가 생겼고, 이를 해결하기 위해 다음 configuration 을 추가함
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/");
    }
}
