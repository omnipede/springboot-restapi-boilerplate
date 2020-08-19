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
public class MemberSignin {
    @NotEmpty(message = "Member's username is required")
    @Pattern(regexp = "(^[가-힣a-zA-Z]*$)", message = "Wrong username format")
    private String username;
}
