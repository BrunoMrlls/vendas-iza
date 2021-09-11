package br.com.iza.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import br.com.iza.controller.dto.costumer.CostumerInputDTO;
import br.com.iza.domain.Costumer;
import br.com.iza.repository.CostumerRepository;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CostumerService {

    @Resource
    private CostumerRepository costumerRepository;

    @Resource
    private CostumerFinder finder;

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
        return finder.findByIdentifier(identifier);
    }
}
