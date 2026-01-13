package br.com.foodhub.core.application.dto.menu.items;

public record UpdateMenuItemDTO(
        String restaurantId,
        String menuId,
        String menuItemId,
        String name,
        String description,
        Double price,
        boolean inRestaurantOnly,
        String photograph
) {}
