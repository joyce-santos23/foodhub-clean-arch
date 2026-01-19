package br.com.foodhub.core.application.usecase.menu;

import br.com.foodhub.core.application.dto.menu.MenuWithItemsResultDTO;
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
class ListRestaurantMenuUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @InjectMocks
    ListRestaurantMenuUseCase useCase;

    @Test
    void shouldListMenusWithItemsWhenRestaurantExists() {
        Restaurant restaurant = restaurantWithMenus();

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        List<MenuWithItemsResultDTO> result =
                useCase.execute("r1");

        assertEquals(1, result.size());
        assertEquals("Menu Principal", result.get(0).name());
        assertEquals(1, result.get(0).items().size());
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute("r1"));
    }

    /* =========================
       HELPERS
       ========================= */

    private Restaurant restaurantWithMenus() {
        MenuItem item = MenuItem.reconstitute(
                "item1",
                "Pizza",
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

        Restaurant restaurant = Restaurant.reconstitute(
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

        return restaurant;
    }
}
