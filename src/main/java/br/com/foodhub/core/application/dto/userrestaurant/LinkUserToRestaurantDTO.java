package br.com.foodhub.core.application.dto.userrestaurant;

public record LinkUserToRestaurantDTO(
        String restaurantId,
        String userTypeId
) {
}
