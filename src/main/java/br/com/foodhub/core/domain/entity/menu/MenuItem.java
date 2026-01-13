package br.com.foodhub.core.domain.entity.menu;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class MenuItem {
    private String id;
    private String name;
    private String description;
    private Double price;
    private boolean inRestaurantOnly;
    private String photograph;

    public MenuItem(
            String name,
            String description,
            Double price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        this.name = require(name, "Nome do item");
        this.description = description;
        this.price = validatePrice(require(price, "Preço"));
        this.inRestaurantOnly = inRestaurantOnly;
        this.photograph = normalizeOptional(photograph);
    }

    /* =========================
       Validações de domínio
       ========================= */
    public void update(
            String name,
            String description,
            Double price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        this.name = require(name, "Nome do item");
        this.description = description;
        this.price = validatePrice(require(price, "Preço"));
        this.inRestaurantOnly = inRestaurantOnly;
        this.photograph = normalizeOptional(photograph);
    }

    private Double validatePrice(Double price) {
        if (price < 0) {
            throw new BusinessRuleViolationException(
                "O preço do item não pode ser negativo"
            );
        }
        return price;
    }

    private String normalizeOptional(String value) {
        if (value == null) return null;
        if (value.isBlank()) {
            throw new RequiredFieldException("Fotografia não pode ser vazio");
        }
        return value;
    }

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
