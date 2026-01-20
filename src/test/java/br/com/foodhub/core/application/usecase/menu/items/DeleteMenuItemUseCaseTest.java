package br.com.foodhub.core.application.usecase.menu.items;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.application.port.user.UserGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.menu.MenuItem;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.entity.user.User;
import br.com.foodhub.core.domain.entity.user.UserType;
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
class DeleteMenuItemUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    DeleteMenuItemUseCase useCase;

    @Test
    void shouldDeleteMenuItemSuccessfully() {
        User user = ownerUser();
        Restaurant restaurant = restaurantWithMenuAndItem();

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        useCase.execute("u1", "r1", "menu1", "item1");

        verify(restaurantGateway).save(restaurant);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", "menu1", "item1"));

        verifyNoInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(ownerUser()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", "menu1", "item1"));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenMenuNotFound() {
        User user = ownerUser();
        Restaurant restaurant = restaurantWithoutMenu();

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("u1", "r1", "menu-inexistente", "item1"));

        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowWhenMenuItemNotFound() {
        User user = ownerUser();
        Restaurant restaurant = restaurantWithMenuWithoutItem();

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(RuntimeException.class,
                () -> useCase.execute("u1", "r1", "menu1", "item-inexistente"));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private User ownerUser() {
        UserType ownerType = UserType.reconstitute(
                "type-owner",
                "OWNER"
        );

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                "hashed",
                null,
                ownerType
        );
    }

    private Restaurant restaurantWithMenuAndItem() {
        MenuItem item = MenuItem.reconstitute(
                "item1",
                "pizza",
                "Pizza de queijo",
                39.90,
                false,
                null
        );

        Menu menu = Menu.reconstitute(
                "menu1",
                "Menu Principal",
                List.of(item)
        );

        return Restaurant.reconstitute(
                "r1",
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                null,
                List.of(menu)
        );
    }

    private Restaurant restaurantWithMenuWithoutItem() {
        Menu menu = Menu.reconstitute(
                "menu1",
                "Menu Principal",
                List.of()
        );

        return Restaurant.reconstitute(
                "r1",
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                null,
                List.of(menu)
        );
    }

    private Restaurant restaurantWithoutMenu() {
        return Restaurant.reconstitute(
                "r1",
                "Restaurante Teste",
                "12345678000190",
                "Italiana",
                "u1",
                "addr1",
                "100",
                null,
                null,
                List.of()
        );
    }
}
