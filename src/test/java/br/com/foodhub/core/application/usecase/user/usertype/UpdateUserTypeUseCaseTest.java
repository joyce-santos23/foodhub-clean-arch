package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
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
class UpdateUserTypeUseCaseTest {

    @Mock
    UserTypeGateway gateway;

    @InjectMocks
    UpdateUserTypeUseCase useCase;

    @Test
    void shouldUpdateUserTypeSuccessfully() {
        UserType type = UserType.reconstitute("type1", "WAITER");

        when(gateway.findById("type1"))
                .thenReturn(Optional.of(type));

        when(gateway.save(any()))
                .thenReturn(type);

        UserTypeRequestDTO dto = new UserTypeRequestDTO("COOK");

        var result = useCase.execute("type1", dto);

        assertEquals("COOK", result.name());
        verify(gateway).save(type);
    }

    @Test
    void shouldThrowWhenUserTypeDoesNotExist() {
        when(gateway.findById("type1"))
                .thenReturn(Optional.empty());

        UserTypeRequestDTO dto = new UserTypeRequestDTO("COOK");

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("type1", dto));

        verify(gateway, never()).save(any());
    }
}
