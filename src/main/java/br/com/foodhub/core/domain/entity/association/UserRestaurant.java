package br.com.foodhub.core.domain.entity.association;

import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class UserRestaurant {

    private final String userId;
    private final String restaurantId;
    private final String userTypeId;

    public UserRestaurant(
            String userId,
            String restaurantId,
            String userTypeId
    ) {
        this.userId = require(userId, "Usuário");
        this.restaurantId = require(restaurantId, "Restaurante");
        this.userTypeId = require(userTypeId, "Tipo de usuário");
    }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
