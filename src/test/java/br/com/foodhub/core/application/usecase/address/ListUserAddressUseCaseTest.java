package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserAddressUseCaseTest {

    @Mock
    UserGateway gateway;

    @InjectMocks
    ListUserAddressUseCase useCase;

    @Test
    void shouldListUserAddressesSuccessfully() {
        User user = userWithAddresses();

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        List<UserAddressResultDTO> result =
                useCase.execute("u1");

        assertEquals(2, result.size());

        UserAddressResultDTO first = result.get(0);
        assertEquals("addr1", first.addressBaseId());
        assertTrue(first.primary());

        UserAddressResultDTO second = result.get(1);
        assertEquals("addr2", second.addressBaseId());
        assertFalse(second.primary());

        verify(gateway).findById("u1");
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(gateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1"));

        verify(gateway).findById("u1");
        verifyNoMoreInteractions(gateway);
    }

    /* =========================
       HELPERS
       ========================= */

    private User userWithAddresses() {
        UserType type = UserType.reconstitute("type-customer", "CUSTOMER");

        User user = User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                "hashed",
                "type1",
                type
        );

        UserAddress addr1 = new UserAddress(
                "u1",
                "addr1",
                "100",
                null,
                true
        );

        UserAddress addr2 = new UserAddress(
                "u1",
                "addr2",
                "200",
                "Apto 10",
                false
        );

        user.addAddress(addr1);
        user.addAddress(addr2);

        return user;
    }
}
