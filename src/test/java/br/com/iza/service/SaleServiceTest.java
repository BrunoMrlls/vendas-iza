package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.iza.controller.dto.sale.SaleInputDTO;
import br.com.iza.controller.dto.sale.SaleItemDTO;
import br.com.iza.domain.Costumer;
import br.com.iza.domain.PaymentForm;
import br.com.iza.domain.Product;
import br.com.iza.domain.Sale;
import br.com.iza.domain.SaleStatus;
import br.com.iza.repository.SaleRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @Mock
    private SaleRepository repo;

    @Mock
    private ProductFinder productFinder;

    @Mock
    private CostumerFinder costumerFinder;

    @InjectMocks
    private SaleService underTest;

    private final ArgumentCaptor<Sale> captor = ArgumentCaptor.forClass(Sale.class);

    @Test @DisplayName("Deve testar inserção de nova venda")
    public void shouldTestInsertNewSale() {

        //arrangers
        var productIdentifier = UUID.randomUUID().toString();
        arrangeExpectedProduct(productIdentifier);

        var itemSale = SaleItemDTO.builder()
                                        .productIdentifier(productIdentifier)
                                        .quantity(10)
                                        .unitValue(BigDecimal.ONE)
                                    .build();

        var costumerIdentifier = UUID.randomUUID().toString();
        arrangeExpectedCostumer(costumerIdentifier);

        var expectedSale = SaleInputDTO.builder()
                                    .costumerIdentifier(costumerIdentifier)
                                    .paymentForm(PaymentForm.CASH)
                                    .status(SaleStatus.OPENED)
                                    .items(Collections.singletonList(itemSale))
                                .build();


        //act
        underTest.insert(expectedSale);

        verify(repo, times(1)).save(captor.capture());

        //assertions
        var returnedSale = captor.getValue();

        assertEquals(expectedSale.getCostumerIdentifier(), returnedSale.getCostumer().getIdentifier());
        assertEquals(expectedSale.getPaymentForm(), returnedSale.getPaymentForm());
        assertEquals(expectedSale.getItems().size(), returnedSale.getItems().size());
        assertEquals(expectedSale.getItems().stream().findFirst().get().getProductIdentifier(), returnedSale.getItems().stream().findFirst().get().getProduct().getIdentifier());
        assertEquals(returnedSale.getTotal(), BigDecimal.TEN);

    }

    private void arrangeExpectedProduct(String productIdentifier) {
        var product = Product.builder().value(BigDecimal.TEN).name("Sabão").build();
        product.setIdentifier(productIdentifier);
        when(productFinder.findByIdentifier(eq(productIdentifier))).thenReturn(product);
    }

    private void arrangeExpectedCostumer(String uuid) {
        var costumer = Costumer.builder().name("Balzahar").build();
        costumer.setIdentifier(uuid);
        when(costumerFinder.findByIdentifier(eq(uuid))).thenReturn(costumer);
    }


}
