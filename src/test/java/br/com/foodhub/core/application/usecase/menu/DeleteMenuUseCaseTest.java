package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    DeleteMenuUseCase useCase;

    @Test
    void shouldDeleteMenuSuccessfully() {
        User user = ownerUser();
        Restaurant restaurant = restaurantWithMenu("r1", "u1", "m1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        useCase.execute("u1", "r1", "m1");

        verify(restaurantGateway).save(restaurant);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", "m1"));

        verifyNoInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(ownerUser()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", "m1"));

        verify(restaurantGateway).findById("r1");
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenUserCannotManageRestaurant() {
        User user = customerUser();
        Restaurant restaurant = restaurantWithMenu("r1", "u2", "m1");

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(BusinessRuleViolationException.class,
                () -> useCase.execute("u1", "r1", "m1"));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User ownerUser() {
        UserType owner = UserType.reconstitute("type-owner", "OWNER");

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                owner
        );
    }

    private User customerUser() {
        UserType customer = UserType.reconstitute("type-customer", "CUSTOMER");

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                null,
                "hashed",
                customer
        );
    }

    private Restaurant restaurantWithMenu(String restaurantId, String ownerId, String menuId) {
        Restaurant restaurant = Restaurant.reconstitute(
                restaurantId,
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                ownerId,
                "addr1",
                "100",
                null,
                null,
                null
        );

        restaurant.addMenu(Menu.reconstitute(menuId, "Menu Teste", List.of()));

        return restaurant;
    }

}
