package br.com.iza.controller;

import br.com.iza.controller.dto.ProductInputDTO;
import br.com.iza.controller.dto.ProductOutputDTO;
import br.com.iza.domain.Product;
import br.com.iza.service.ProductService;
import java.net.URI;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid ProductInputDTO productDTO) {
        Product product = productService.insert(productDTO);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{identifier}")
            .buildAndExpand(product.getIdentifier())
        .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{identifier}")
    public ResponseEntity<ProductOutputDTO> findIdentity(@PathVariable(name = "identifier") String identifier) {
        ProductOutputDTO output = productService.findBy(identifier).toOutputDTO();
        return ResponseEntity.ok(output);
    }

}
