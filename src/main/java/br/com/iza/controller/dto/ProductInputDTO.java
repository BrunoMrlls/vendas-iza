package br.com.iza.controller.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDTO {

    @NotBlank(message = "Field name must be not empty.")
    private String name;

    @DecimalMin(value = "0")
    private BigDecimal valor;

}
