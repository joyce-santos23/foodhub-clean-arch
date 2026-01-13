package br.com.foodhub.core.application.dto.menu.items;

public record MenuItemResultDTO(
        String id,
        String name,
        String description,
        Double price,
        boolean inRestaurantOnly,
        String photograph
) {}

