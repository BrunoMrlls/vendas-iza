package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.iza.domain.Costumer;
import br.com.iza.repository.CostumerRepository;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CostumerFinderTest {

    @Mock
    private CostumerRepository repo;

    @InjectMocks
    private CostumerFinder finder;

    @Test
    @DisplayName("Deve testar erro cliente não encontrado")
    void itShouldThrowCostumerNotFoundExceptionTest() {
        Assertions.assertThatThrownBy(() -> finder.findByIdentifier("123"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Costumer not found.");
    }

    @Test @DisplayName("Deve testar a procura de cliente")
    void itShouldFindCostumerByIdentifierTest() {
        //given
        String identifier = UUID.randomUUID().toString();

        Costumer expeted = Costumer.builder().name("José").build();

        expeted.setIdentifier(identifier);
        //when
        when(repo.findByIdentifier(eq(identifier))).thenReturn(expeted);

        //then
        Costumer returned = finder.findByIdentifier(identifier);
        assertEquals(expeted, returned);
    }

}
