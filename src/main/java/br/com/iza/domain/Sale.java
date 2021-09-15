package br.com.iza.domain;

import br.com.iza.controller.dto.sale.SaleOutputDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sale extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status = SaleStatus.OPENED;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    @Enumerated(EnumType.STRING)
    private PaymentForm paymentForm;

    @Column(scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "sale", orphanRemoval = true)
    private List<SaleItem> items;

    @Builder
    public Sale(Integer number, SaleStatus status, Costumer costumer, PaymentForm paymentForm, BigDecimal total, LocalDateTime updatedAt,
        List<SaleItem> items) {
        this.number = number;
        this.status = status;
        this.costumer = costumer;
        this.paymentForm = paymentForm;
        this.total = total;
        this.updatedAt = updatedAt;
        this.items = items;
    }

    public SaleOutputDTO toOutputDTO() {
        return SaleOutputDTO.builder()
                .costumerIdentifier(getCostumer().getIdentifier())
                .number(getNumber())
                .paymentForm(getPaymentForm())
                .status(getStatus())
                .total(getTotal())
                .updatedAt(getUpdatedAt())
                .items(getItems().stream().map(SaleItem::toOutputDTO).collect(Collectors.toList()))
        .build();
    }
}
