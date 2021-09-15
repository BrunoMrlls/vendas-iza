package br.com.iza.repository;

import br.com.iza.domain.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SaleRepository extends BaseRepository<Sale> {
    @Query(value = "select count(s) from Sale s where cast(s.createdAt as LocalDate) = :date ")
    int countSaleByCreatedAt(@Param("date") LocalDate date);
}
