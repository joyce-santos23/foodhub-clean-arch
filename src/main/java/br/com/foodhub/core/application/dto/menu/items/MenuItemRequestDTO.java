package br.com.foodhub.core.application.dto.menu.items;

public record MenuItemRequestDTO(
        String restaurantId,
        String menuId,
        String name,
        String description,
        Double price,
        boolean inRestaurantOnly,
        String photograph
) {
}
