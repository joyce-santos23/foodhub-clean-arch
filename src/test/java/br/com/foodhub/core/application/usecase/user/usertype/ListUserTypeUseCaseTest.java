package br.com.foodhub.core.application.usecase.user.usertype;

import br.com.foodhub.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.core.application.port.user.UserTypeGateway;
import br.com.foodhub.core.domain.entity.user.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserTypeUseCaseTest {

    @Mock
    UserTypeGateway gateway;

    @InjectMocks
    ListUserTypeUseCase useCase;

    @Test
    void shouldReturnListOfUserTypes() {
        when(gateway.findAll()).thenReturn(List.of(
                UserType.reconstitute("1", "OWNER"),
                UserType.reconstitute("2", "WAITER")
        ));

        List<UserTypeResultDTO> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("OWNER", result.get(0).name());
        assertEquals("WAITER", result.get(1).name());

        verify(gateway).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoUserTypesExist() {
        when(gateway.findAll()).thenReturn(List.of());

        List<UserTypeResultDTO> result = useCase.execute();

        assertTrue(result.isEmpty());
        verify(gateway).findAll();
    }
}
