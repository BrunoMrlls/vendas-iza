package br.com.iza.repository;

import br.com.iza.domain.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    Costumer findByIdentifier(String identifier);
}
