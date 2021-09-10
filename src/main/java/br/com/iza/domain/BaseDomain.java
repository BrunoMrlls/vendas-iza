package br.com.iza.domain;


import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseDomain {

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = true, updatable = false)
    private String identifier;

    public BaseDomain() {
        this.createdAt = LocalDateTime.now();
        this.identifier = UUID.randomUUID().toString();
    }

}
