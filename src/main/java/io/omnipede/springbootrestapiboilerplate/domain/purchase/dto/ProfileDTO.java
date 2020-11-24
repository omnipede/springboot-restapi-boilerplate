package io.omnipede.springbootrestapiboilerplate.domain.purchase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    @Min(1)
    @Max(1024)
    @NotNull
    Long id;
}
