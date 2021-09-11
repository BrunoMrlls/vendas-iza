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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
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
