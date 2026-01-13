package br.com.foodhub.core.domain.entity.menu;

import br.com.foodhub.core.domain.exceptions.generic.DomainException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Menu {
    private String id;
    private String name;
    private List<MenuItem> items;

    public Menu(String name) {
        this.name = require(name, "Nome do menu");
        this.items = new ArrayList<>();
    }

    /* =========================
       Comportamento do menu
       ========================= */

    public void addItem(MenuItem item) {
        require(item, "Item do menu");

        boolean exists = items.stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(item.getName()));

        if (exists) {
            throw new ResourceConflictException(
                    "Já existe um item com esse nome no menu"
            );
        }

        this.items.add(item);
    }

    public void removeItem(String itemId) {

        boolean removed = this.items.removeIf(item -> itemId.equals(item.getId()));

        if (!removed) {
            throw new DomainException("Item não pertence ao menu");
        }
    }

    public List<MenuItem> getItems() {
        return List.copyOf(items);
    }

    public MenuItem getItemById(String itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() ->
                        new DomainException("Item não pertence ao menu")
                );
    }

    /* =========================
       Validação interna
       ========================= */

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private <T> T require(T value, String field) {
        if (value == null) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
