package br.com.foodhub.core.domain.entity.association;

import br.com.foodhub.core.domain.entity.association.UserRestaurant;
import br.com.foodhub.core.domain.exceptions.generic.RequiredFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserRestaurantTest {

    @Test
    @DisplayName("Deve manter a integridade dos dados ao criar a associação")
    void shouldMaintainDataIntegrity() {
        var userId = "user-123";
        var restaurantId = "rest-456";
        var userTypeId = "admin-type";

        UserRestaurant association = new UserRestaurant(userId, restaurantId, userTypeId);

        assertThat(association.getUserId()).isEqualTo(userId);
        assertThat(association.getRestaurantId()).isEqualTo(restaurantId);
        assertThat(association.getUserTypeId()).isEqualTo(userTypeId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Deve impedir a criação com dados em branco ou nulos")
    void shouldPreventInvalidData(String invalidValue) {

        assertThatThrownBy(() -> new UserRestaurant(null, "rest", "type"))
                .isInstanceOf(RequiredFieldException.class)
                .hasMessageContaining("Usuário");

        assertThatThrownBy(() -> new UserRestaurant("user", invalidValue, "type"))
                .isInstanceOf(RequiredFieldException.class)
                .hasMessageContaining("Restaurante");
    }
}
