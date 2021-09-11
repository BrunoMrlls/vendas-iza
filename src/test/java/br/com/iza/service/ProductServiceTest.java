package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.iza.controller.dto.product.ProductInputDTO;
import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    @Mock
    private ProductFinder finder;

    @InjectMocks
    private ProductService underTest;


    @Test @DisplayName("Deve testar inserção de produto")
    void productInsertTest() {
        //given
        String productName = "TV 29'";
        var input = ProductInputDTO.builder().name(productName).valor(BigDecimal.TEN).build();

        //when
        underTest.insert(input);

        //then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repo).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(capturedProduct.getName(), productName);
    }

    @Test @DisplayName("Deve testar a procura do produto")
    void itShouldFindProductByIdentifierTest() {
        //given
        String identifier = UUID.randomUUID().toString();
        String productName = "Macbook air";

        Product expeted = new Product(productName, BigDecimal.TEN);
        //when
        when(finder.findByIdentifier(eq(identifier))).thenReturn(expeted);

        //then
        Product returned = underTest.findBy(identifier);
        assertEquals(expeted, returned);
    }

    @Test @DisplayName("Deve testar erro de campo obrigatório")
    void itShouldThrowProductInsertIllegalArgumentErrorTest() {
        Assertions.assertThatThrownBy(() -> underTest.insert(ProductInputDTO.builder().valor(BigDecimal.TEN).build()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Property name is required.");

        verify(repo, never()).save(any());
    }

}