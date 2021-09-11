package br.com.iza.controller.dto.sale;

import br.com.iza.domain.PaymentForm;
import br.com.iza.domain.SaleStatus;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleInputDTO {

    @NotNull
    private SaleStatus status;

    @NotBlank
    private String costumerIdentifier;

    @NotNull
    private PaymentForm paymentForm;

    @Min(value = 1)
    private List<SaleItemDTO> items;

}
