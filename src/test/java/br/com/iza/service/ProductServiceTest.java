package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.iza.domain.Product;
import br.com.iza.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;
    private ProductService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(repo);
    }

    @Test
    void productInsertTest() {
        //given
        String productName = "TV 29'";

        //when
        underTest.insert(productName);

        //then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repo).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(capturedProduct.getName(), productName);
    }

    @Test
    void productFindByNameTest() {
        //given
        String productName = "Sofá 3 lugares";

        Product expeted = new Product(productName);
        //when
        when(repo.findByName(productName)).thenReturn(expeted);

        //then
        Product returned = underTest.findByName(productName);

        assertEquals(expeted, returned);
    }

    @Test
    void itShouldThrowProductInsertIllegalArgumentErrorTest() {
        Assertions.assertThatThrownBy(
            () -> underTest.insert(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Parâmetro nome do produto é obrigatório.");

        verify(repo, never()).save(any());
    }

    @Test
    void list() {
        //when
        underTest.list();
        //then
        verify(repo).findAll();
    }
}