package br.com.foodhub.core.application.dto.userrestaurant;

import br.com.foodhub.core.domain.entity.association.UserRestaurant;

public record UserRestaurantResultDTO(
        String userId,
        String restaurantId,
        String userTypeId
) {
    public static UserRestaurantResultDTO from(
            String userId,
            UserRestaurant r
    ) {
        return new UserRestaurantResultDTO(
                userId,
                r.getRestaurantId(),
                r.getUserTypeId()
        );
    }
}

