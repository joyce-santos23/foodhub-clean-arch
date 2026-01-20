package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.core.application.port.security.PasswordHasherGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
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
class CreateUserUseCaseTest {

    @Mock
    UserGateway userGateway;

    @Mock
    UserTypeGateway userTypeGateway;

    @Mock
    PasswordHasherGateway passwordHasherGateway;

    @InjectMocks
    CreateUserUseCase useCase;

    /* =========================
       SUCESSO
       ========================= */

    @Test
    void shouldCreateUserSuccessfully() {
        UserType type = UserType.reconstitute("type1", "OWNER");

        when(userTypeGateway.findById("type1"))
                .thenReturn(Optional.of(type));

        when(passwordHasherGateway.hash(any()))
                .thenReturn("hashed");

        when(userGateway.existsByEmail(any())).thenReturn(false);
        when(userGateway.existsByPhone(any())).thenReturn(false);

        when(userGateway.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute(validUserRequest());

        assertEquals("john@email.com", result.email());

        verify(passwordHasherGateway).hash("123");
        verify(userGateway).save(any(User.class));
    }

    /* =========================
       ERROS
       ========================= */

    @Test
    void shouldThrowWhenUserTypeDoesNotExist() {
        when(userTypeGateway.findById("type1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(validUserRequest()));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        UserType type = UserType.reconstitute("type1", "OWNER");

        when(userTypeGateway.findById("type1"))
                .thenReturn(Optional.of(type));

        when(passwordHasherGateway.hash(any()))
                .thenReturn("hashed");

        when(userGateway.existsByEmail(any()))
                .thenReturn(true);

        assertThrows(ResourceConflictException.class,
                () -> useCase.execute(validUserRequest()));

        verify(userGateway, never()).save(any());
    }

    private UserRequestDTO validUserRequest() {
        return new UserRequestDTO(
                "John",
                "john@email.com",
                "119",
                null,
                "123",
                "type1"
        );
    }

}
