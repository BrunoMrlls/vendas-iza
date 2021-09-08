package br.com.iza.domain;

import br.com.iza.controller.dto.ProductOutputDTO;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Product extends BaseDomain {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, scale = 2)
    private BigDecimal valor;

    public Product(String name) {
        this.name = name;
    }

    public ProductOutputDTO toOutputDTO() {
        return ProductOutputDTO.builder()
                .identifier(getIdentifier())
                .name(getName())
                .valor(getValor())
                .created_at(getCreated_at())
            .build();
    }
}
