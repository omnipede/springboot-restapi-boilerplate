package io.omnipede.rest.domain.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("member_id")
    @NotNull(message = "member_id is required")
    private Long memberId;

    @JsonProperty("product_name")
    @NotEmpty(message = "Product name is required")
    private String productName;
}
