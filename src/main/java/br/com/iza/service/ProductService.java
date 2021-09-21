package br.com.iza.service;

import br.com.iza.controller.dto.product.ProductInputDTO;
import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductFinder finder;

    public Product insert(ProductInputDTO dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            throw new IllegalArgumentException("Property name is required.");
        }

        var product = Product.builder()
                                .name(dto.getName())
                                .value(dto.getValue())
                               .build();

        return productRepository.save(product);
    }

    public Product findBy(String identifier) {
        return finder.findByIdentifier(identifier);
    }

}
