package io.omnipede.springbootrestapiboilerplate.topic;

import javax.validation.constraints.*;

public class TopicDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    public TopicDTO(@NotEmpty String id, @NotEmpty String name, @NotEmpty String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
