package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserAddressUseCaseTest {

    @Mock
    FindOrCreateAddressBaseUseCase findOrCreateAddressBaseUseCase;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    CreateUserAddressUseCase useCase;

    @Test
    void shouldCreateUserAddressSuccessfully() {
        User user = user();
        AddressBase addressBase = addressBase();

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(findOrCreateAddressBaseUseCase.execute("01001000"))
                .thenReturn(addressBase);

        when(userGateway.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        UserAddressDTO dto = new UserAddressDTO(
                "01001000",
                "100",
                "Apto 12",
                true
        );

        UserAddressResultDTO result =
                useCase.execute("u1", dto);

        assertNotNull(result.id());
        assertEquals("u1", result.userId());
        assertEquals("addr1", result.addressBaseId());
        assertTrue(result.primary());
        assertEquals("100", result.number());
        assertEquals("Apto 12", result.complement());

        verify(userGateway).save(user);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        UserAddressDTO dto = new UserAddressDTO(
                "01001000",
                "100",
                null,
                true
        );

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", dto));

        verifyNoInteractions(findOrCreateAddressBaseUseCase);
        verify(userGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User user() {
        UserType type = UserType.reconstitute("type-customer", "CUSTOMER");

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                type
        );
    }

    private AddressBase addressBase() {
        return AddressBase.reconstitute(
                "addr1",
                "01001000",
                "Rua Teste",
                "Centro",
                "São Paulo",
                "SP",
                "BR"
        );
    }
}
