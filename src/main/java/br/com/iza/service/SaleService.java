package br.com.iza.service;

import br.com.iza.controller.dto.sale.SaleInputDTO;
import br.com.iza.domain.Sale;
import br.com.iza.domain.SaleItem;
import br.com.iza.repository.SaleRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaleService {

    @Resource
    private final SaleRepository saleRepository;

    @Resource
    private final ProductFinder productFinder;

    @Resource
    private final CostumerFinder costumerFinder;

    public Sale insert(SaleInputDTO input) {

        List<SaleItem> itemsSale = input.getItems().stream()
            .map(
                item -> SaleItem.builder()
                        .product(productFinder.findByIdentifier(item.getProductIdentifier()))
                        .quantity(item.getQuantity())
                        .unitValue(item.getUnitValue())
                    .build()
                )
            .collect(Collectors.toList());

        var sale = Sale.builder()
                .costumer(costumerFinder.findByIdentifier(input.getCostumerIdentifier()))
                .number(1)
                .items(itemsSale)
                .paymentForm(input.getPaymentForm())
                .status(input.getStatus())
                .total(itemsSale.stream()
                    .map(
                        saleItem ->
                            BigDecimal.valueOf(saleItem.getQuantity()).multiply(saleItem.getUnitValue()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
            .build();

        return saleRepository.save(sale);
    }
}
