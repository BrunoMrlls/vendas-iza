package br.com.iza.controller.dto.sale;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDTO {

    @NotBlank
    private String productIdentifier;

    @Min(value = 1L)
    private Integer quantity;

    @DecimalMin(value = "0.01")
    @NotNull
    private BigDecimal unitValue;

    private BigDecimal getTotal() {
        return unitValue.multiply(BigDecimal.valueOf(quantity));
    }

}
