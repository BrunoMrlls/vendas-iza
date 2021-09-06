package br.com.iza.service;

import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import java.util.List;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    @Resource
    private ProductRepository productRepository;

    public void insert(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Parâmetro nome do produto é obrigatório.");
        }
        productRepository.save(new Product(name));
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }

}
