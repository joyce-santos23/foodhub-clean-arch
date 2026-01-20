package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUseCaseTest {

    @Mock
    UserTypeGateway gateway;

    @InjectMocks
    CreateUserTypeUseCase useCase;

    @Test
    void shouldCreateUserTypeSuccessfully() {

        when(gateway.save(any(UserType.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        UserTypeRequestDTO dto = new UserTypeRequestDTO("WAITER");

        var result = useCase.execute(dto);

        assertNotNull(result);
        assertEquals("WAITER", result.name());

        verify(gateway).save(any(UserType.class));
    }


    @Test
    void shouldThrowWhenNameIsNull() {

        UserTypeRequestDTO dto = new UserTypeRequestDTO(null);

        assertThrows(RequiredFieldException.class,
                () -> useCase.execute(dto));

        verify(gateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenNameIsBlank() {

        UserTypeRequestDTO dto = new UserTypeRequestDTO("   ");

        assertThrows(RequiredFieldException.class,
                () -> useCase.execute(dto));

        verify(gateway, never()).save(any());
    }
}
