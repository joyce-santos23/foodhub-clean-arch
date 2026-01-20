package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAllRestaurantUseCaseTest {

    @Mock
    RestaurantGateway gateway;

    @InjectMocks
    ListAllRestaurantUseCase useCase;

    @Test
    void shouldListRestaurantsPaginated() {
        Restaurant r1 = restaurant("r1");
        Restaurant r2 = restaurant("r2");

        PageResultDTO<Restaurant> page = new PageResultDTO<>(
                List.of(r1, r2),
                0,
                10,
                2,
                1
        );

        when(gateway.findAll(0, 10)).thenReturn(page);

        PageRequestDTO request = new PageRequestDTO(0, 10);

        var result = useCase.execute(request);

        assertEquals(2, result.content().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());

        verify(gateway).findAll(0, 10);
    }

    /* ========= Helper ========= */

    private Restaurant restaurant(String id) {
        return Restaurant.reconstitute(
                id,
                "Restaurante " + id,
                "12345678000190",
                "Italiana",
                "owner1",
                "addr1",
                "100",
                null,
                null,
                null
        );
    }
}

