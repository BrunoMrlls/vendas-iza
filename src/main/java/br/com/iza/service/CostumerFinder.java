package br.com.iza.service;

import br.com.iza.domain.Costumer;
import br.com.iza.repository.CostumerRepository;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CostumerFinder {

    @Resource
    private CostumerRepository repository;

    public Costumer findByIdentifier(String identifier) {
        var costumer = repository.findByIdentifier(identifier);
        if (Optional.ofNullable(costumer).isEmpty()) {
            throw new ResourceNotFoundException("Costumer not found.");
        }
        return costumer;
    }

}
