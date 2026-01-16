package br.com.foodhub.infra.web.payload.menu.items;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de item de menu")
public record MenuItemResponsePayload(

        @Schema(example = "69697bfb4a50530b33363161")
        String id,

        @Schema(example = "Pizza Margherita")
        String name,

        @Schema(example = "Pizza com molho de tomate, mussarela e manjericão")
        String description,

        @Schema(example = "49.90")
        Double price,

        @Schema(example = "false")
        boolean inRestaurantOnly,

        @Schema(example = "https://cdn.foodhub.com/items/pizza.jpg")
        String photograph
) {}
