package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class UserType {

    private String id;
    private String name;

    private UserType(String id, String name) {
        this.id = id;
        this.name = require(name, "Nome do tipo de usuário").trim().toUpperCase();
    }

    public UserType(String name) {
        this(null, name);
    }

    public static UserType reconstitute(String id, String name) {
        return new UserType(id, name);
    }

    public boolean isRestaurantRelated() {
        return !("CUSTOMER".equals(name) || "OWNER".equals(name));
    }

    public void rename(String newName) {
        if (isSystemType()) {
            throw new BusinessRuleViolationException(
                    "Não é permitido alterar tipos de usuário do sistema"
            );
        }
        this.name = require(newName, "Nome do tipo de usuário");
    }

    public boolean isSystemType() {
        return "OWNER".equals(name) || "CUSTOMER".equals(name);
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

