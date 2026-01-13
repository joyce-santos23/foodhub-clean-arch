package br.com.foodhub.core.application.dto.userrestaurant;

public record UnlinkUserFromRestaurantDTO(
        String userId,
        String restaurantId
) {
}
