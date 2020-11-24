package io.omnipede.springbootrestapiboilerplate.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO {
    @NotEmpty(message = "Name of product is required")
    @Pattern(regexp="(^[가-힣a-zA-Z0-9]*$)", message="Wrong product name format")
    String name;
}
