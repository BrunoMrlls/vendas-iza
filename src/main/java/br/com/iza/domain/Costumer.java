package br.com.iza.domain;

import br.com.iza.controller.dto.costumer.CostumerOutputDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Costumer extends BaseDomain{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String phoneNumber;

    @Builder
    public Costumer(String name, String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public CostumerOutputDTO toOutputDTO() {
        return CostumerOutputDTO.builder()
            .createdAt(getCreatedAt())
            .identifier(getIdentifier())
            .name(getName())
            .phoneNumber(getPhoneNumber())
        .build();
    }
}
