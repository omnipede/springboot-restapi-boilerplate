package io.omnipede.springbootrestapiboilerplate.topic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

@ApiModel(value="Topic request", description="Topic api request model")
public class TopicDTO {
    @NotEmpty
    @ApiModelProperty(value="Topic id", allowEmptyValue=false)
    private String id;
    @NotEmpty
    @ApiModelProperty(value="Topic name", allowEmptyValue=false)
    private String name;
    @NotEmpty
    @ApiModelProperty(value="Topic description", allowEmptyValue=false)
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
