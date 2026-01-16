package br.com.foodhub.core.application.dto.restaurant;

public record RestaurantRequestDTO(
        String businessName,
        String cnpj,
        String cuisineType,
        String cep,
        String numberStreet,
        String complement
) {
}
