package br.com.foodhub.core.application.dto.menu.items;

public record DeleteMenuItemDTO(
        String restaurantId,
        String menuId,
        String menuItemId
) {}
