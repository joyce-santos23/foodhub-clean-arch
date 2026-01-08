package br.com.foodhub.core.domain.entity.user;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class UserType {
    private String id;
    private String name;
    private boolean restaurantRelated;

    public UserType(String name, boolean restaurantRelated) {
        this.name = require(name, "Nome do tipo de usuário");
        this.restaurantRelated = restaurantRelated;
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
