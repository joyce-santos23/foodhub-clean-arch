package br.com.foodhub.core.application.usecase.restaurant;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
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
class DeleteRestaurantUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    DeleteRestaurantUseCase useCase;

    @Test
    void shouldDeleteRestaurantWhenUserCanManage() {
        User user = userOwner();
        Restaurant restaurant = restaurant("r1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        useCase.execute("u1", "r1");

        verify(restaurantGateway).deleteById("r1");
    }


    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1"));

        verifyNoInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(userOwner()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1"));

        verify(restaurantGateway, never()).deleteById(any());
    }

    @Test
    void shouldThrowWhenUserCannotManageRestaurant() {
        User user = userWithoutPermission();
        Restaurant restaurant = restaurant("r1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(RuntimeException.class,
                () -> useCase.execute("u1", "r1"));

        verify(restaurantGateway, never()).deleteById(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User userOwner() {
        UserType ownerType = UserType.reconstitute(
                "type-owner",
                "OWNER"
        );

        User user = User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                ownerType
        );

        user.isLinkedToRestaurant("r1");

        return user;
    }


    private User userWithoutPermission() {
        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                null
        );
    }

    private Restaurant restaurant(String id) {
        return Restaurant.reconstitute(
                id,
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                null,
                null
        );
    }
}
