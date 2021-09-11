package br.com.iza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.iza.controller.dto.costumer.CostumerInputDTO;
import br.com.iza.domain.Costumer;
import br.com.iza.repository.CostumerRepository;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConstumerServiceTest {

    @Mock
    private CostumerRepository repo;

    @Mock
    private CostumerFinder finder;

    @InjectMocks
    private CostumerService underTest;

    private ArgumentCaptor<Costumer> captor = ArgumentCaptor.forClass(Costumer.class);

    @Test @DisplayName("Deve gravar um novo cliente")
    public void itShouldInsertNewCostumerTest() {
        //arrange
        CostumerInputDTO inputDTO = new CostumerInputDTO("Bruno Meireles", "91 982392844");

        // act
        underTest.insert(inputDTO);
        verify(repo, times(1)).save(captor.capture());

        //assert
        assertEquals(captor.getValue().getName(), inputDTO.getName());
    }

    @Test @DisplayName("Deve lançar erro de campo obrigatório")
    public void itShouldThrowIllegalExceptionRequiredNameTest() {
        //arrange
        CostumerInputDTO inputDTO = new CostumerInputDTO();

        //act
        Assertions.assertThatThrownBy(() -> underTest.insert(inputDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Property name is required");

        //assert
        verify(repo, never()).save(any());
    }

    @Test @DisplayName("Deve buscar cliente pelo indentificador")
    public void itShouldFindCostumerByIdentifierTest() {
        //arrange
        final String uui = UUID.randomUUID().toString();
        Costumer expected = Costumer.builder().name("José").build();
        when(finder.findByIdentifier(any())).thenReturn(expected);

        //act
        Costumer returned = underTest.findBy(uui);
        //assert
        assertEquals(expected.getIdentifier(), returned.getIdentifier());
    }

}
