package br.com.iza.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOutputDTO {

    private String identifier;
    private String name;
    private BigDecimal valor;
    private LocalDateTime created_at;

}
