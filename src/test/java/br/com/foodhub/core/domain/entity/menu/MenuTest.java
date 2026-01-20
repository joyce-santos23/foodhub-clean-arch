package br.com.foodhub.core.domain.entity.menu;

import br.com.foodhub.core.domain.exceptions.generic.DomainException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {


    @Test
    void shouldCreateMenuSuccessfully() {
        Menu menu = new Menu("Cardápio Principal");

        assertNotNull(menu.getId());
        assertEquals("Cardápio Principal", menu.getName());
        assertTrue(menu.getItems().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(RequiredFieldException.class,
                () -> new Menu(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(RequiredFieldException.class,
                () -> new Menu(" "));
    }

    @Test
    void shouldReconstituteMenuSuccessfully() {
        MenuItem item1 = createItem("Pizza");
        MenuItem item2 = createItem("Hambúrguer");

        Menu menu = Menu.reconstitute(
                "menu-123",
                "Cardápio Reconstituído",
                List.of(item1, item2)
        );

        assertEquals("menu-123", menu.getId());
        assertEquals("Cardápio Reconstituído", menu.getName());
        assertEquals(2, menu.getItems().size());
        assertTrue(menu.getItems().contains(item1));
        assertTrue(menu.getItems().contains(item2));
    }


    @Test
    void shouldAddItemSuccessfully() {
        Menu menu = createMenu();
        MenuItem item = createItem("Pizza");

        menu.addItem(item);

        assertEquals(1, menu.getItems().size());
        assertTrue(menu.getItems().contains(item));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullItem() {
        Menu menu = createMenu();

        assertThrows(RequiredFieldException.class,
                () -> menu.addItem(null));
    }

    @Test
    void shouldNotAllowDuplicateItem() {
        Menu menu = createMenu();
        MenuItem item = createItem("Pizza");

        menu.addItem(item);

        assertThrows(ResourceConflictException.class,
                () -> menu.addItem(item));
    }

    @Test
    void shouldRemoveItemSuccessfully() {
        Menu menu = createMenu();
        MenuItem item = createItem("Pizza");

        menu.addItem(item);
        menu.removeItem(item.getId());

        assertTrue(menu.getItems().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistingItem() {
        Menu menu = createMenu();

        assertThrows(DomainException.class,
                () -> menu.removeItem("invalid-id"));
    }

    @Test
    void shouldGetItemByIdSuccessfully() {
        Menu menu = createMenu();
        MenuItem item = createItem("Pizza");

        menu.addItem(item);

        MenuItem found = menu.getItemById(item.getId());

        assertEquals(item, found);
    }

    @Test
    void shouldThrowExceptionWhenItemNotFoundById() {
        Menu menu = createMenu();

        assertThrows(ResourceNotFoundException.class,
                () -> menu.getItemById("invalid-id"));
    }

    @Test
    void getItemsShouldReturnImmutableList() {
        Menu menu = createMenu();

        List<MenuItem> items = menu.getItems();

        assertThrows(UnsupportedOperationException.class,
                () -> items.add(createItem("Pizza")));
    }

    private Menu createMenu() {
        return new Menu("Cardápio");
    }

    private MenuItem createItem(String name) {
        return new MenuItem(
                name,
                "Descrição",
                30.0,
                false,
                null
        );
    }
}
