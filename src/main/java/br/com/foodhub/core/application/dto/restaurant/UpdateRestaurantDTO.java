package br.com.foodhub.core.application.dto.restaurant;

public record UpdateRestaurantDTO(
        String businessName,
        String cuisineType,
        String numberStreet,
        String complement

) {
}
