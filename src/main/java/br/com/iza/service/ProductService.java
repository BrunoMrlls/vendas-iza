package br.com.iza.service;

import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    @Resource
    private ProductRepository productRepository;

    public Product insert(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Property name is required.");
        }
        return productRepository.save(new Product(name));
    }

    public Product findBy(String identifier) {
        Product product = productRepository.findProductByIdentifier(identifier);
        if (Optional.ofNullable(product).isEmpty()) {
            throw new IllegalArgumentException("Product not found.");
        }
        return product;
    }

}
