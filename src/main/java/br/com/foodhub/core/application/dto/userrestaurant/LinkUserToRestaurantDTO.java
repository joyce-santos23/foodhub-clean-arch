package br.com.foodhub.core.application.dto.userrestaurant;

public record LinkUserToRestaurantDTO(
        String userId,
        String restaurantId,
        String userTypeId
) {
}
