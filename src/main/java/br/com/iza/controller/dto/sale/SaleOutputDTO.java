package br.com.iza.controller.dto.sale;

import br.com.iza.domain.PaymentForm;
import br.com.iza.domain.SaleStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleOutputDTO {
    private Integer number;
    private SaleStatus status;
    private String costumerIdentifier;
    private PaymentForm paymentForm;
    private BigDecimal total;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    private List<SaleItemDTO> items;
}
