package br.com.foodhub.core.application.usecase.address;

import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.address.AddressBase;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserAddressUseCaseTest {

    @Mock
    UserGateway gateway;

    @InjectMocks
    DeleteUserAddressUseCase useCase;

    @Test
    void shouldDeleteUserAddressSuccessfully() {
        User user = userWithAddress("addr-user-1");

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(gateway.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        useCase.execute("u1", "addr-user-1");

        verify(gateway).save(user);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(gateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "addr-user-1"));

        verify(gateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenAddressDoesNotBelongToUser() {
        User user = userWithAddress("other-address");

        when(gateway.findById("u1"))
                .thenReturn(Optional.of(user));

        assertThrows(AddressNotBelongsToUserException.class,
                () -> useCase.execute("u1", "addr-user-1"));

        verify(gateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User userWithAddress(String userAddressId) {
        UserType type = UserType.reconstitute("type-customer", "CUSTOMER");

        User user = User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                type
        );

        AddressBase base = AddressBase.reconstitute(
                "addr-base-1",
                "01001000",
                "Rua Teste",
                "Centro",
                "São Paulo",
                "SP",
                "BR"
        );

        UserAddress address = UserAddress.reconstitute(
                userAddressId,
                user.getId(),
                base.getId(),
                "100",
                null,
                true
        );

        user.addAddress(address);
        return user;
    }

}
