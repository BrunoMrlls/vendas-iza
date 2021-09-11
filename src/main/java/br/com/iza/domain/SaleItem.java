package br.com.iza.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}