package br.com.foodhub.core.application.dto.menu;

public record MenuRequestDTO(
        String restaurantId,
        String name
) {
}
