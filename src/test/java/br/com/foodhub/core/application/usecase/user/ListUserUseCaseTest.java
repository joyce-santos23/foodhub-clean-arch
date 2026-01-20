package br.com.foodhub.core.application.usecase.user;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.dto.user.UserResultDTO;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.user.User;
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
class ListUserUseCaseTest {

    @Mock
    UserGateway gateway;

    @InjectMocks
    ListUserUseCase useCase;

    @Test
    void shouldListUsersWithPagination() {
        // arrange
        PageRequestDTO request = new PageRequestDTO(0, 10);

        UserType owner = UserType.reconstitute("type1", "OWNER");

        User user1 = User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                owner
        );

        User user2 = User.reconstitute(
                "u2",
                "Mary",
                "mary@email.com",
                "11888888888",
                null,
                "hashed",
                owner
        );

        PageResultDTO<User> pageFromGateway = new PageResultDTO<>(
                List.of(user1, user2),
                0,
                10,
                2,
                1
        );

        when(gateway.findAll(0, 10))
                .thenReturn(pageFromGateway);

        // act
        PageResultDTO<UserResultDTO> result = useCase.execute(request);

        // assert
        assertEquals(2, result.content().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());

        assertEquals("john@email.com", result.content().get(0).email());
        assertEquals("mary@email.com", result.content().get(1).email());

        verify(gateway).findAll(0, 10);
    }
}
