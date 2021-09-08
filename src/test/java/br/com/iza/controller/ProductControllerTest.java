package br.com.iza.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.iza.controller.dto.ProductInputDTO;
import br.com.iza.domain.Product;
import br.com.iza.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

    @Test @DisplayName("Deve inserir um produto")
    void insert() throws Exception {

        Product insert = new Product("Playstation 5");

        when(productService.insert(any())).thenReturn(insert);

        ProductInputDTO content = new ProductInputDTO("Playstation 5", BigDecimal.TEN);

        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(prettyWriter.writeValueAsBytes(content))
            )
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test @DisplayName("Deve consultar um produto")
    void findIdentity() throws Exception {

        final String uuid = UUID.randomUUID().toString();

        Product expected = Product
            .builder()
                .identifier(uuid)
                .name("Playstation 5")
            .build();

        when(productService.findBy(any())).thenReturn(expected);

        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/product/{identifier}", uuid)
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(prettyWriter.writeValueAsString(expected.toOutputDTO())));

    }
}