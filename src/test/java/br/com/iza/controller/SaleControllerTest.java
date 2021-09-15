package br.com.iza.controller;

import br.com.iza.controller.dto.sale.SaleInputDTO;
import br.com.iza.controller.dto.sale.SaleItemDTO;
import br.com.iza.domain.*;
import br.com.iza.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;

@Disabled
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {SaleController.class})
public class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService underService;

    @InjectMocks
    private SaleController underController;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test @DisplayName("Deve testar inserção venda 201 e location no header")
    public void shouldReturn201AndLocationInHeader() {
        //arrangers
        var inputItemSale = SaleItemDTO.builder()
                .productIdentifier(UUID.randomUUID().toString())
                .quantity(10)
                .unitValue(BigDecimal.ONE)
                .build();

        final String uuiCostumer = UUID.randomUUID().toString();

        var inputSale = SaleInputDTO.builder()
                .costumerIdentifier(uuiCostumer)
                .paymentForm(PaymentForm.CASH)
                .status(SaleStatus.OPENED)
                .items(Collections.singletonList(inputItemSale))
                .build();

        var itemSale = SaleItem.builder()
                .quantity(10)
                .unitValue(BigDecimal.ONE)
                .build();

        var costumer = Costumer.builder().name("José").build();
        costumer.setIdentifier(uuiCostumer);

        var expectedSale = Sale.builder()
                .costumer(costumer)
                .paymentForm(PaymentForm.CASH)
                .status(SaleStatus.OPENED)
                .items(Collections.singletonList(itemSale))
                .build();

        when(underService.insert(any())).thenReturn(expectedSale);


        //acts
        ResponseEntity<Void> response = underController.insert(inputSale);

        //assertions
        verify(underService).insert(inputSale);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertTrue(response.getHeaders().get(LOCATION).get(0).endsWith(String.format("%s", expectedSale.getIdentifier())));
    }

}
