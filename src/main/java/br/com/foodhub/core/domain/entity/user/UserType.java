package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class UserType {
    private String id;
    private String name;
    private boolean restaurantRelated;

    public UserType(String name) {
        this.name = require(name, "Nome do tipo de usuário");
        this.restaurantRelated = defineRestaurantRelated(name);
    }

    public void rename(String newName) {

        if (isSystemType()) {
            throw new BusinessRuleViolationException(
                    "Não é permitido alterar tipos de usuário do sistema"
            );
        }

        this.name = require(newName, "Nome do tipo de usuário");
        this.restaurantRelated = defineRestaurantRelated(this.name);
    }

    public boolean isSystemType() {
        return "OWNER".equals(name) || "CUSTOMER".equals(name);
    }

    private boolean defineRestaurantRelated(String name) {
        return !("CUSTOMER".equals(name) || "OWNER".equals(name));
    }

    public boolean isOwner() { return "OWNER".equals(name); }
    public boolean isCustomer() { return "CUSTOMER".equals(name); }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
