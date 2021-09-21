package br.com.iza.controller.dto.product;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInputDTO {

    @NotBlank(message = "Field name must not be empty.")
    private String name;

    @DecimalMin(value = "0")
    private BigDecimal value;

}
