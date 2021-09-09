package br.com.iza.controller;

import br.com.iza.controller.dto.costumer.CostumerInputDTO;
import br.com.iza.controller.dto.costumer.CostumerOutputDTO;
import br.com.iza.service.CostumerService;
import java.net.URI;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/costumer")
public class CostumerController {

    @Resource
    private CostumerService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid CostumerInputDTO input) {
        var costumer = service.insert(input);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{identifier}")
            .buildAndExpand(costumer.getIdentifier())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{identifier}")
    public ResponseEntity<CostumerOutputDTO> findBy(@PathVariable(name = "identifier") String identifier) {
        return ResponseEntity.ok(service.findBy(identifier).toOutputDTO());
    }
}
