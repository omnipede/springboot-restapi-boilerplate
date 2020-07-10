package io.omnipede.springbootrestapiboilerplate.topic;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * Request body bean
 * Java bean validation 사용
 * @see "https://medium.com/@SlackBeck/javabean-validation%EA%B3%BC-hibernate-validator-%EA%B7%B8%EB%A6%AC%EA%B3%A0-spring-boot-3f31aee610f5"
 */
@Getter
@Setter
public class TopicDTO {
    @NotEmpty(message = "Topic id is required")
    private String id;
    @NotEmpty(message = "Topic name is required")
    private String name;
    @NotEmpty(message = "Topic description is required")
    private String description;

    public TopicDTO(@NotEmpty String id, @NotEmpty String name, @NotEmpty String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
