package br.com.iza.controller;

import br.com.iza.controller.dto.sale.SaleInputDTO;
import br.com.iza.controller.dto.sale.SaleOutputDTO;
import br.com.iza.domain.Sale;
import br.com.iza.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;

@RestController()
@RequestMapping(value = "/api/sale")
public class SaleController {

    @Resource
    private SaleService saleService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody SaleInputDTO input) {
        Sale sale = saleService.insert(input);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{identifier}")
                .buildAndExpand(sale.getIdentifier())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{identifier}")
    public ResponseEntity<SaleOutputDTO> findBy(@PathVariable(value = "identifier") String identifier) {
        return ResponseEntity.ok(saleService.findBy(identifier).toOutputDTO());
    }

}
