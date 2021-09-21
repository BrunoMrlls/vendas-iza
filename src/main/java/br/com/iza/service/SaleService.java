package br.com.iza.service;

import br.com.iza.controller.dto.sale.SaleInputDTO;
import br.com.iza.domain.Product;
import br.com.iza.domain.Sale;
import br.com.iza.domain.SaleItem;
import br.com.iza.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
                item -> {
                    Product product = productFinder.findByIdentifier(item.getProductIdentifier());
                    return SaleItem.builder()
                            .product(product)
                            .unitValue(item.getUnitValue() != null ? item.getUnitValue() : product.getValue())
                            .quantity(item.getQuantity())
                        .build();
                    }
                )
            .collect(Collectors.toList());

        var sale = Sale.builder()
                .costumer(costumerFinder.findByIdentifier(input.getCostumerIdentifier()))
                .number(saleRepository.countSaleByCreatedAt(LocalDate.now()))
                .paymentForm(input.getPaymentForm())
                .status(input.getStatus())
                .total(itemsSale.stream()
                    .map(
                        saleItem ->
                            BigDecimal.valueOf(saleItem.getQuantity()).multiply(saleItem.getUnitValue())
                    ).reduce(BigDecimal.ZERO, BigDecimal::add))
            .build();
        sale.addItems(itemsSale);

        return saleRepository.save(sale);
    }

    public Sale findBy(String identifier) {
        var sale = saleRepository.findByIdentifier(identifier);
        if (sale == null) {
            throw new ResourceNotFoundException("Sale not found.");
        }
        return sale;
    }
}
