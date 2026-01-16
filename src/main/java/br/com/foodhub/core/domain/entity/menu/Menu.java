package br.com.foodhub.core.domain.entity.menu;

import br.com.foodhub.core.domain.exceptions.generic.DomainException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Menu {
    private String id;
    private String name;
    private List<MenuItem> items;

    public Menu(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = require(name, "Nome do menu");
        this.items = new ArrayList<>();
    }

    private Menu(String id, String name, List<MenuItem> items) {
        this.id = id;
        this.name = name;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public static Menu reconstitute(String id, String name, List<MenuItem> items) {
        return new Menu(id, name, items);
    }

    /* =========================
       Comportamento do menu
       ========================= */

    public void addItem(MenuItem item) {
        require(item, "Item do menu");

        boolean exists = items.stream()
                .anyMatch(i -> i.equals(item));

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
                        new ResourceNotFoundException("Item não encontrado com o id informado!")
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
