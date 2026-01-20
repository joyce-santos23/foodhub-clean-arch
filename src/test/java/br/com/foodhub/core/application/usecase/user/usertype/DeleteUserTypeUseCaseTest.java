package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseTest {

    @Mock
    UserTypeGateway gateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    DeleteUserTypeUseCase useCase;

    @Test
    void shouldThrowWhenUserTypeDoesNotExist() {
        when(gateway.findById("type1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("type1"));

        verify(gateway, never()).delete(any());
    }

    @Test
    void shouldThrowWhenUserTypeIsSystemType() {
        UserType systemType = UserType.reconstitute("type1", "OWNER");

        when(gateway.findById("type1"))
                .thenReturn(Optional.of(systemType));

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("type1"));

        verify(gateway, never()).delete(any());
    }

    @Test
    void shouldThrowWhenUserTypeIsLinkedToUsers() {
        UserType type = UserType.reconstitute("type1", "WAITER");

        when(gateway.findById("type1"))
                .thenReturn(Optional.of(type));

        when(userGateway.existsUserWithUserType("type1"))
                .thenReturn(true);

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("type1"));

        verify(gateway, never()).delete(any());
    }

    @Test
    void shouldDeleteUserTypeSuccessfully() {
        UserType type = UserType.reconstitute("type1", "WAITER");

        when(gateway.findById("type1"))
                .thenReturn(Optional.of(type));

        when(userGateway.existsUserWithUserType("type1"))
                .thenReturn(false);

        useCase.execute("type1");

        verify(gateway).delete("type1");
    }
}
