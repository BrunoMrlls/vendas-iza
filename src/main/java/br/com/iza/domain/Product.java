package br.com.iza.domain;

import br.com.iza.controller.dto.product.ProductOutputDTO;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product extends BaseDomain {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, scale = 2)
    private BigDecimal valor;

    @Builder
    public Product(String name, BigDecimal valor) {
        super();
        this.name = name;
        this.valor = valor;
    }

    public ProductOutputDTO toOutputDTO() {
        return ProductOutputDTO.builder()
                .identifier(getIdentifier())
                .name(getName())
                .valor(getValor())
                .createdAt(getCreatedAt())
            .build();
    }
}
