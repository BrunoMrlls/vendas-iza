package br.com.iza.controller.dto.costumer;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CostumerInputDTO {
    @NotBlank
    private String name;
    private String phoneNumber;
}
