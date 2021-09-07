package br.com.iza.domain;


import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class BaseDomain {

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private String identifier;

    public BaseDomain() {
        this.created_at = LocalDateTime.now();
        this.identifier = UUID.randomUUID().toString();
    }

}
