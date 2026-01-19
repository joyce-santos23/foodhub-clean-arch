package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ListRestaurantByIdUseCaseTest {

    @Mock
    RestaurantGateway gateway;

    @InjectMocks
    ListRestaurantByIdUseCase useCase;

    @Test
    void shouldReturnRestaurantById() {
        Restaurant restaurant = restaurant("r1");

        when(gateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        var result = useCase.execute("r1");

        assertEquals("r1", result.restaurantId());
        verify(gateway).findById("r1");
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(gateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1"));
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

