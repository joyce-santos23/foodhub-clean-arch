package br.com.foodhub.core.domain.entity.menu;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {


    @Test
    void shouldCreateMenuItemSuccessfully() {
        MenuItem item = new MenuItem(
                "Pizza Margherita",
                "Clássica",
                35.0,
                false,
                null
        );

        assertNotNull(item.getId());
        assertEquals("pizza margherita", item.getName()); // normalizado
        assertEquals(35.0, item.getPrice());
        assertFalse(item.isInRestaurantOnly());
        assertNull(item.getPhotograph());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(RequiredFieldException.class, () ->
                new MenuItem(null, "desc", 10.0, false, null)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(RequiredFieldException.class, () ->
                new MenuItem("   ", "desc", 10.0, false, null)
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThrows(RequiredFieldException.class, () ->
                new MenuItem("Pizza", "desc", null, false, null)
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(BusinessRuleViolationException.class, () ->
                new MenuItem("Pizza", "desc", -1.0, false, null)
        );
    }

    @Test
    void shouldThrowExceptionWhenPhotographIsBlank() {
        assertThrows(RequiredFieldException.class, () ->
                new MenuItem("Pizza", "desc", 10.0, false, "   ")
        );
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        MenuItem item = createValidItem();

        item.update(
                "Pizza Quatro Queijos",
                "Nova descrição",
                42.0,
                true,
                "photo.jpg"
        );

        assertEquals("Pizza Quatro Queijos", item.getName());
        assertEquals("Nova descrição", item.getDescription());
        assertEquals(42.0, item.getPrice());
        assertTrue(item.isInRestaurantOnly());
        assertEquals("photo.jpg", item.getPhotograph());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithInvalidName() {
        MenuItem item = createValidItem();

        assertThrows(RequiredFieldException.class, () ->
                item.update(" ", "desc", 10.0, false, null)
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNegativePrice() {
        MenuItem item = createValidItem();

        assertThrows(BusinessRuleViolationException.class, () ->
                item.update("Pizza", "desc", -5.0, false, null)
        );
    }

    @Test
    void itemsWithSameNameShouldBeEqual() {
        MenuItem item1 = new MenuItem(
                "Pizza",
                "Desc 1",
                30.0,
                false,
                null
        );

        MenuItem item2 = new MenuItem(
                "pizza",
                "Desc 2",
                40.0,
                true,
                null
        );

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void shouldReconstituteMenuItemSuccessfully() {
        MenuItem item = MenuItem.reconstitute(
                "item-123",
                "Pizza Persistida",
                "Do banco",
                50.0,
                true,
                "img.png"
        );

        assertEquals("item-123", item.getId());
        assertEquals("Pizza Persistida", item.getName());
        assertEquals(50.0, item.getPrice());
        assertTrue(item.isInRestaurantOnly());
        assertEquals("img.png", item.getPhotograph());
    }

    private MenuItem createValidItem() {
        return new MenuItem(
                "Pizza",
                "Desc",
                30.0,
                false,
                null
        );
    }
}
