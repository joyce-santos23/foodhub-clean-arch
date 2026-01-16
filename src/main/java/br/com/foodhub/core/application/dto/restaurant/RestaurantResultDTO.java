package br.com.foodhub.core.application.dto.restaurant;

import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.core.domain.entity.restaurant.Restaurant;

import java.util.List;

public record RestaurantResultDTO(
        String restaurantId,
        String businessName,
        String cnpj,
        String cuisineType,
        String ownerId,
        String addressBaseId,
        String numberStreet,
        String complement,
        List<OpeningHours> openingHours
) {

    public static RestaurantResultDTO from(Restaurant restaurant) {
        return new RestaurantResultDTO(
                restaurant.getId(),
                restaurant.getBusinessName(),
                restaurant.getCnpj(),
                restaurant.getCuisineType(),
                restaurant.getOwnerId(),
                restaurant.getAddressBaseId(),
                restaurant.getNumberStreet(),
                restaurant.getComplement(),
                restaurant.getOpeningHours() == null
                        ? List.of()
                        : restaurant.getOpeningHours()
        );
    }
}
