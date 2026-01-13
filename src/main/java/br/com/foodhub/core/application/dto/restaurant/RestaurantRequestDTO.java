package br.com.foodhub.core.application.dto.restaurant;

import br.com.foodhub.core.domain.entity.restaurant.OpeningHours;

import java.util.List;

public record RestaurantRequestDTO(
        String businessName,
        String cnpj,
        String cuisineType,
        String ownerId,
        String addressBaseId,
        String numberStreet,
        String complement
) {
}
