package br.com.iza.domain;

import br.com.iza.controller.dto.sale.SaleItemDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, scale = 2)
    private BigDecimal unitValue;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;

    @Transient
    private BigDecimal getTotal() {
        return unitValue.multiply(BigDecimal.valueOf(quantity));
    }

    public SaleItemDTO toOutputDTO() {
        return SaleItemDTO.builder()
                .productIdentifier(getProduct().getIdentifier())
                .quantity(getQuantity())
                .unitValue(getUnitValue())
            .build();
    }
}