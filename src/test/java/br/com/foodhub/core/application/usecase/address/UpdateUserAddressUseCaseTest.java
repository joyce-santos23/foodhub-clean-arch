package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.association.UserAddress;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.user.AddressNotBelongsToUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserAddressUseCaseTest {

    @Mock
    UserGateway gateway;

    @InjectMocks
    UpdateUserAddressUseCase useCase;

    @Test
    void shouldUpdateUserAddressSuccessfully() {
        User user = userWithAddresses();

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        UpdateUserAddressDTO dto =
                new UpdateUserAddressDTO("200", "Apto 20", false);

        UserAddressResultDTO result =
                useCase.execute("u1", addressId(user), dto);

        assertEquals("200", result.number());
        assertEquals("Apto 20", result.complement());
        assertTrue(result.primary());

        verify(gateway).save(user);
    }

    @Test
    void shouldUpdateAndSetPrimaryAddress() {
        User user = userWithAddresses();

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        UpdateUserAddressDTO dto =
                new UpdateUserAddressDTO("300", null, true);

        UserAddressResultDTO result =
                useCase.execute("u1", addressId(user), dto);

        assertTrue(result.primary());
        verify(gateway).save(user);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(gateway.findById("u1"))
                .thenReturn(Optional.empty());

        UpdateUserAddressDTO dto =
                new UpdateUserAddressDTO("100", null, false);

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "addr1", dto));

        verify(gateway).findById("u1");
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void shouldThrowWhenAddressDoesNotBelongToUser() {
        User user = userWithAddresses();

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        UpdateUserAddressDTO dto =
                new UpdateUserAddressDTO("100", null, false);

        assertThrows(AddressNotBelongsToUserException.class,
                () -> useCase.execute("u1", "invalid-address", dto));

        verify(gateway).findById("u1");
        verify(gateway, never()).save(any());
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

        UserAddress address1 = new UserAddress(
                "u1",
                "addr1",
                "100",
                null,
                true
        );

        UserAddress address2 = new UserAddress(
                "u1",
                "addr2",
                "150",
                "Casa",
                false
        );

        user.addAddress(address1);
        user.addAddress(address2);

        return user;
    }

    private String addressId(User user) {
        return user.getAddresses().get(0).getId();
    }
}
