package br.com.foodhub.core.application.usecase.menu.items;

import br.com.foodhub.core.application.dto.menu.items.MenuItemRequestDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {

    @Mock
    RestaurantGateway restaurantGateway;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    UpdateMenuItemUseCase useCase;

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        User user = ownerUser();
        Restaurant restaurant = restaurantWithMenuAndItem();

        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(user));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurant));

        MenuItemRequestDTO dto = new MenuItemRequestDTO(
                "Pizza Atualizada",
                "Nova descrição",
                49.90,
                true,
                "foto.png"
        );

        var result = useCase.execute(
                "u1",
                "r1",
                "menu1",
                "item1",
                dto
        );

        assertEquals("item1", result.id());
        assertEquals("Pizza Atualizada", result.name());
        assertEquals(49.90, result.price());
        assertTrue(result.inRestaurantOnly());

        verify(restaurantGateway).save(restaurant);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(
                        "u1", "r1", "menu1", "item1", validDto()
                ));

        verifyNoInteractions(restaurantGateway);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(ownerUser()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(
                        "u1", "r1", "menu1", "item1", validDto()
                ));
    }

    @Test
    void shouldThrowWhenMenuNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(ownerUser()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurantWithoutMenu()));

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(
                        "u1", "r1", "menu-invalido", "item1", validDto()
                ));
    }

    @Test
    void shouldThrowWhenItemNotFound() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(ownerUser()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurantWithMenuWithoutItem()));

        assertThrows(ResourceNotFoundException.class,
                () -> useCase.execute(
                        "u1", "r1", "menu1", "item-invalido", validDto()
                ));
    }

    @Test
    void shouldThrowWhenUserCannotManageRestaurant() {
        when(userGateway.findById("u1"))
                .thenReturn(Optional.of(userWithoutPermission()));

        when(restaurantGateway.findById("r1"))
                .thenReturn(Optional.of(restaurantWithMenuAndItem()));

        assertThrows(RuntimeException.class,
                () -> useCase.execute(
                        "u1", "r1", "menu1", "item1", validDto()
                ));

        verify(restaurantGateway, never()).save(any());
    }

    /* =========================
       HELPERS
       ========================= */

    private MenuItemRequestDTO validDto() {
        return new MenuItemRequestDTO(
                "Pizza Atualizada",
                "Nova descrição",
                49.90,
                true,
                null
        );
    }

    private User ownerUser() {
        UserType ownerType = UserType.reconstitute("type-owner", "OWNER");

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

    private User userWithoutPermission() {
        UserType customerType = UserType.reconstitute("type-customer", "CUSTOMER");

        return User.reconstitute(
                "u1",
                "John",
                "john@email.com",
                "11999999999",
                "hashed",
                null,
                customerType
        );
    }

    private Restaurant restaurantWithMenuAndItem() {
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
