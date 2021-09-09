package br.com.iza.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import br.com.iza.controller.dto.costumer.CostumerInputDTO;
import br.com.iza.domain.Costumer;
import br.com.iza.repository.CostumerRepository;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CostumerService {

    @Resource
    private CostumerRepository costumerRepository;

    public Costumer insert(CostumerInputDTO inputDTO) {
        if (isEmpty(inputDTO.getName())) {
            throw new IllegalArgumentException("Property name is required");
        }
        var costumer = Costumer.builder()
                                    .name(inputDTO.getName())
                                    .phoneNumber(inputDTO.getPhoneNumber())
                                .build();

       return costumerRepository.save(costumer);
    }

    public Costumer findBy(String identifier) {
        var costumer = costumerRepository.findByIdentifier(identifier);
        if (Optional.ofNullable(costumer).isEmpty()) {
            throw new ResourceNotFoundException("Costumer not found.");
        }
        return costumer;
    }
}
