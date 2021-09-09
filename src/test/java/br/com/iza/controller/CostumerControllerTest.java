package br.com.iza.controller;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.iza.controller.dto.costumer.CostumerInputDTO;
import br.com.iza.controller.dto.costumer.CostumerOutputDTO;
import br.com.iza.domain.Costumer;
import br.com.iza.service.CostumerService;
import br.com.iza.service.ResourceNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CostumerController.class)
public class CostumerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CostumerService service;

    @InjectMocks
    private CostumerController controller;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test @DisplayName("Deve inserir novo cliente")
    public void itShouldInsertNewCostumer() {
        // arrange
        var input = CostumerInputDTO.builder().name("Alfred").build();
        var expected = Costumer.builder().name("Alfred").build();

        when(service.insert(input)).thenReturn(expected);

        //act
        ResponseEntity<Void> response = controller.insert(input);
        final String locationExpected = format("/%s", expected.getIdentifier());

        // assert
        verify(service).insert(input);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertTrue(response.getHeaders().get(LOCATION).get(0).endsWith(locationExpected));
    }

    @Test @DisplayName("Deve buscar cliente")
    public void itShouldFindCostumer() {
        //arrange
        String identifier = UUID.randomUUID().toString();
        var expected = Costumer.builder().name("Breno").build();
        when(service.findBy(identifier)).thenReturn(expected);

        //act
        ResponseEntity<CostumerOutputDTO> response = controller.findBy(identifier);

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getIdentifier(), expected.toOutputDTO().getIdentifier());
        verify(service, times(1)).findBy(identifier);
    }

    @Test @DisplayName("Deve lançar exception de campo nome obrigatório")
    public void itShouldThrownRequiredFieldTest() throws Exception {
        //arrange
        when(service.findBy(any())).thenThrow(ResourceNotFoundException.class);

        //act and assert
        mvc.perform(get("/api/costumer/{identifier}", UUID.randomUUID().toString()))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

}
