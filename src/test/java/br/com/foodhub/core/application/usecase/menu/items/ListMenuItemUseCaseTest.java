package br.com.foodhub.core.application.usecase.menu.items;

import br.com.foodhub.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.entity.menu.MenuItem;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuItemUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @InjectMocks
    ListMenuItemUseCase useCase;

    @Test
    void shouldReturnMenuItemSuccessfully() {
        Restaurant restaurant = restaurantWithMenuAndItem();

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        var result = useCase.execute("r1", "menu1", "item1");

        assertEquals("item1", result.id());
        assertEquals("pizza", result.name());
        assertEquals(39.90, result.price());

        verify(restaurantGateway).findById("r1");
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1", "menu1", "item1"));
    }

    @Test
    void shouldThrowWhenMenuNotFound() {
        Restaurant restaurant = restaurantWithoutMenu();

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1", "menu-inexistente", "item1"));
    }

    @Test
    void shouldThrowWhenItemNotFound() {
        Restaurant restaurant = restaurantWithMenuWithoutItem();

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1", "menu1", "item-inexistente"));
    }

    /* =========================
       HELPERS
       ========================= */

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
                "owner1",
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
                "owner1",
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
                "owner1",
                "addr1",
                "100",
                null,
                null,
                List.of()
        );
    }
}
