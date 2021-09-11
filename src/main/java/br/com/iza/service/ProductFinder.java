package br.com.iza.service;

import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductFinder {

    @Resource
    private ProductRepository repo;

    public Product findByIdentifier(String identifier) {
        Product product = repo.findByIdentifier(identifier);
        if (Optional.ofNullable(product).isEmpty()) {
            throw new ResourceNotFoundException("Product not found.");
        }
        return product;
    }

}
