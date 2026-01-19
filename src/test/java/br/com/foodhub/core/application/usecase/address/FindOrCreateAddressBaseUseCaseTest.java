package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.core.application.port.cep.CepGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindOrCreateAddressBaseUseCaseTest {

    @Mock
    AddressBaseGateway gateway;

    @Mock
    CepGateway cepGateway;

    @InjectMocks
    FindOrCreateAddressBaseUseCase useCase;

    @Test
    void shouldReturnExistingAddressBaseWhenCepAlreadyExists() {
        AddressBase address = AddressBase.reconstitute(
                "addr1",
                "01001000",
                "Rua Teste",
                "Centro",
                "São Paulo",
                "SP",
                "BR"
        );

        when(gateway.findByCep("01001000"))
                .thenReturn(Optional.of(address));

        AddressBase result = useCase.execute("01001-000");

        assertSame(address, result);

        verify(gateway).findByCep("01001000");
        verifyNoInteractions(cepGateway);
        verify(gateway, never()).save(any());
    }

    @Test
    void shouldCreateAddressBaseWhenCepDoesNotExist() {
        AddressBase fromApi = AddressBase.reconstitute(
                null,
                "01001000",
                "Rua Teste",
                "Centro",
                "São Paulo",
                "SP",
                "BR"
        );

        AddressBase saved = AddressBase.reconstitute(
                "addr1",
                "01001000",
                "Rua Teste",
                "Centro",
                "São Paulo",
                "SP",
                "BR"
        );

        when(gateway.findByCep("01001000"))
                .thenReturn(Optional.empty());

        when(cepGateway.lookup("01001000"))
                .thenReturn(fromApi);

        when(gateway.save(fromApi))
                .thenReturn(saved);

        AddressBase result = useCase.execute("01001-000");

        assertEquals("addr1", result.getId());

        verify(gateway).findByCep("01001000");
        verify(cepGateway).lookup("01001000");
        verify(gateway).save(fromApi);
    }
}
