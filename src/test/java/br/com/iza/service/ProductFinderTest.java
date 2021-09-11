package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductFinderTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductFinder finder;

    @Test @DisplayName("Deve testar erro de produto não encontrado")
    void itShouldThrowProductNotFoundExceptionTest() {
        Assertions.assertThatThrownBy(() -> finder.findByIdentifier("123"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Product not found.");
    }

    @Test @DisplayName("Deve testar a procura do produto")
    void itShouldFindProductByIdentifierTest() {
        //given
        String identifier = UUID.randomUUID().toString();
        String productName = "Macbook air";

        Product expeted = new Product(productName, BigDecimal.TEN);
        expeted.setIdentifier(identifier);
        //when
        when(repo.findByIdentifier(eq(identifier))).thenReturn(expeted);

        //then
        Product returned = finder.findByIdentifier(identifier);
        assertEquals(expeted, returned);
    }
}