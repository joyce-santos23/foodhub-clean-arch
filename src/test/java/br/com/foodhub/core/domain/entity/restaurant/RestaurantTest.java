package br.com.foodhub.core.domain.entity.restaurant;

import br.com.foodhub.core.domain.entity.menu.Menu;
import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.DomainException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.core.domain.exceptions.restaurant.InvalidCnpjException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {


    @Test
    void shouldCreateRestaurantSuccessfully() {
        Restaurant restaurant = createValidRestaurant();

        assertEquals("Pizzaria Napoli", restaurant.getBusinessName());
        assertEquals("12345678000190", restaurant.getCnpj());
        assertEquals("Italiana", restaurant.getCuisineType());
        assertEquals("owner1", restaurant.getOwnerId());
    }

    @Test
    void shouldNormalizeCnpj() {
        Restaurant restaurant = new Restaurant(
                "Pizzaria",
                "12.345.678/0001-90",
                "Italiana",
                "owner1",
                "addr1",
                "100",
                null,
                List.of()
        );

        assertEquals("12345678000190", restaurant.getCnpj());
    }

    @Test
    void shouldThrowExceptionForInvalidCnpj() {
        assertThrows(InvalidCnpjException.class,
                () -> new Restaurant(
                        "Pizzaria",
                        "123",
                        "Italiana",
                        "owner1",
                        "addr1",
                        "100",
                        null,
                        List.of()
                ));
    }

    @Test
    void shouldThrowExceptionWhenBusinessNameIsNull() {
        assertThrows(RequiredFieldException.class,
                () -> new Restaurant(
                        null,
                        "12345678000190",
                        "Italiana",
                        "owner1",
                        "addr1",
                        "100",
                        null,
                        List.of()
                ));
    }


    @Test
    void shouldUpdateBasicInfoSuccessfully() {
        Restaurant restaurant = createValidRestaurant();

        restaurant.updateBasicInfo(
                "Nova Pizzaria",
                "Brasileira",
                "200",
                "Sala 2"
        );

        assertEquals("Nova Pizzaria", restaurant.getBusinessName());
        assertEquals("Brasileira", restaurant.getCuisineType());
        assertEquals("200", restaurant.getNumberStreet());
        assertEquals("Sala 2", restaurant.getComplement());
    }


    @Test
    void shouldAddMenuSuccessfully() {
        Restaurant restaurant = createValidRestaurant();
        Menu menu = mockMenu("m1", "Principal");

        restaurant.addMenu(menu);

        assertEquals(1, restaurant.getMenus().size());
    }

    @Test
    void shouldNotAllowDuplicateMenuName() {
        Restaurant restaurant = createValidRestaurant();

        restaurant.addMenu(mockMenu("m1", "Principal"));

        assertThrows(BusinessRuleViolationException.class,
                () -> restaurant.addMenu(mockMenu("m2", "principal")));
    }

    @Test
    void shouldRemoveMenuSuccessfully() {
        Restaurant restaurant = createValidRestaurant();
        Menu menu = mockMenu("m1", "Principal");

        restaurant.addMenu(menu);
        restaurant.removeMenu("m1");

        assertTrue(restaurant.getMenus().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRemovingUnknownMenu() {
        Restaurant restaurant = createValidRestaurant();

        assertThrows(DomainException.class,
                () -> restaurant.removeMenu("invalid-id"));
    }

    @Test
    void shouldGetMenuById() {
        Restaurant restaurant = createValidRestaurant();
        Menu menu = mockMenu("m1", "Principal");

        restaurant.addMenu(menu);

        Menu found = restaurant.getMenuById("m1");

        assertEquals("Principal", found.getName());
    }

    @Test
    void shouldThrowExceptionWhenMenuDoesNotBelongToRestaurant() {
        Restaurant restaurant = createValidRestaurant();

        assertThrows(ResourceNotFoundException.class,
                () -> restaurant.getMenuById("invalid-id"));
    }


    @Test
    void shouldAddOpeningHoursForDay() {
        Restaurant restaurant = createValidRestaurant();

        restaurant.changeOpeningHours(
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        assertEquals(1, restaurant.getOpeningHours().size());
    }

    @Test
    void shouldReplaceOpeningHoursForSameDay() {
        Restaurant restaurant = createValidRestaurant();

        restaurant.changeOpeningHours(
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                false
        );

        restaurant.changeOpeningHours(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(20, 0),
                false
        );

        assertEquals(1, restaurant.getOpeningHours().size());
        assertEquals(LocalTime.of(10, 0),
                restaurant.getOpeningHours().get(0).getOpenTime());
    }

    /* =========================
       HELPERS
       ========================= */

    private Restaurant createValidRestaurant() {
        return new Restaurant(
                "Pizzaria Napoli",
                "12345678000190",
                "Italiana",
                "owner1",
                "address1",
                "100",
                null,
                List.of()
        );
    }

    private Menu mockMenu(String id, String name) {
        return Menu.reconstitute(
                id,
                name,
                List.of()
        );
    }
}
