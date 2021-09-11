package br.com.iza.repository;

import br.com.iza.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
}
