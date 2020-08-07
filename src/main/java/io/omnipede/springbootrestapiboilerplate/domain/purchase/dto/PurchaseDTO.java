package io.omnipede.springbootrestapiboilerplate.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    @NotNull(message = "Member id is required")
    private Long memberId;
    @NotEmpty(message = "Product name is required")
    private String productName;
}
