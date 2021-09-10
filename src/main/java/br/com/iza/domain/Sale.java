package br.com.iza.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;

    @OneToOne
    private Costumer costumer;

    @Enumerated(EnumType.STRING)
    private PaymentForm paymentForm;

    @Column(scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sale", orphanRemoval = true)
    private List<SaleItem> items;

    @Builder
    public Sale(Integer order, SaleStatus status, PaymentForm paymentForm, BigDecimal total, LocalDateTime updatedAt, List<SaleItem> items) {
        super();
        this.order = order;
        this.status = status;
        this.paymentForm = paymentForm;
        this.total = total;
        this.updatedAt = updatedAt;
        this.items = items;
    }
}
