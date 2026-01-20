package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
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
class UpdateUserUseCaseTest {

    @Mock
    UserGateway gateway;

    @InjectMocks
    UpdateUserUseCase useCase;


    @Test
    void shouldUpdateNameSuccessfully() {
        User user = existingUser();

        when(gateway.findById("u1")).thenReturn(Optional.of(user));
        when(gateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserDTO dto = new UpdateUserDTO("New Name", null, null);

        var result = useCase.execute(dto, "u1");

        assertEquals("New Name", result.name());
        verify(gateway).save(user);
    }

    @Test
    void shouldUpdateEmailSuccessfullyWhenNotUsed() {
        User user = existingUser();

        when(gateway.findById("u1")).thenReturn(Optional.of(user));
        when(gateway.existsByEmail(any())).thenReturn(false);
        when(gateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserDTO dto = new UpdateUserDTO(null, "new@email.com", null);

        var result = useCase.execute(dto, "u1");

        assertEquals("new@email.com", result.email());
        verify(gateway).existsByEmail("new@email.com");
    }

    @Test
    void shouldUpdatePhoneSuccessfullyWhenNotUsed() {
        User user = existingUser();

        when(gateway.findById("u1")).thenReturn(Optional.of(user));
        when(gateway.existsByPhone(any())).thenReturn(false);
        when(gateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserDTO dto = new UpdateUserDTO(null, null, "11888888888");

        var result = useCase.execute(dto, "u1");

        assertEquals("11888888888", result.phone());
        verify(gateway).existsByPhone("11888888888");
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(gateway.findById("u1")).thenReturn(Optional.empty());

        UpdateUserDTO dto = new UpdateUserDTO("Name", null, null);

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(dto, "u1"));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User user = existingUser();

        when(gateway.findById("u1")).thenReturn(Optional.of(user));
        when(gateway.existsByEmail(any())).thenReturn(true);

        UpdateUserDTO dto = new UpdateUserDTO(null, "used@email.com", null);

        assertThrows(ResourceConflictException.class,
                () -> useCase.execute(dto, "u1"));

        verify(gateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenPhoneAlreadyExists() {
        User user = existingUser();

        when(gateway.findById("u1")).thenReturn(Optional.of(user));
        when(gateway.existsByPhone(any())).thenReturn(true);

        UpdateUserDTO dto = new UpdateUserDTO(null, null, "11999999998");

        assertThrows(ResourceConflictException.class,
                () -> useCase.execute(dto, "u1"));

        verify(gateway, never()).save(any());
    }


    private User existingUser() {
        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                UserType.reconstitute("type1", "OWNER")
        );
    }
}
