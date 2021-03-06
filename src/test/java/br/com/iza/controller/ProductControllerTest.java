package br.com.iza.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.iza.controller.dto.product.ProductInputDTO;
import br.com.iza.domain.Product;
import br.com.iza.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

    @Test @DisplayName("Deve inserir um produto")
    void insert() throws Exception {

        Product insert = new Product("Playstation 5", BigDecimal.TEN);

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
                .name("Playstation 5")
                .value(BigDecimal.TEN)
            .build();

        when(productService.findBy(any())).thenReturn(expected);


        ObjectMapper mapper = JsonMapper.builder()
            .build().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        var dtoJson = mapper.writeValueAsString(expected.toOutputDTO());
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/product/{identifier}", uuid)
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(dtoJson));

    }
}