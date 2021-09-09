package br.com.iza.service;

import br.com.iza.controller.dto.product.ProductInputDTO;
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

    public Product insert(ProductInputDTO dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            throw new IllegalArgumentException("Property name is required.");
        }

        var product = Product.builder()
                                .name(dto.getName())
                                .valor(dto.getValor())
                               .build();

        return productRepository.save(product);
    }

    public Product findBy(String identifier) {
        Product product = productRepository.findProductByIdentifier(identifier);
        if (Optional.ofNullable(product).isEmpty()) {
            throw new ResourceNotFoundException("Product not found.");
        }
        return product;
    }

}
