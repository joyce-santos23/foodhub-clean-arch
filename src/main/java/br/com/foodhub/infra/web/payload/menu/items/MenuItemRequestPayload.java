package br.com.foodhub.infra.web.payload.menu.items;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload para criação de item de menu")
public record MenuItemRequestPayload(
        @NotBlank
        @Schema(
                description = "Nome do item",
                example = "Pizza Margherita"
        )
        String name,

        @Schema(
                description = "Descrição do item",
                example = "Pizza com molho de tomate, mussarela e manjericão"
        )
        String description,

        @NotNull
        @Schema(
                description = "Preço do item",
                example = "49.90"
        )
        Double price,

        @Schema(
                description = "Indica se o item é consumido apenas no restaurante",
                example = "false"
        )
        boolean inRestaurantOnly,

        @Schema(
                description = "URL da foto do item",
                example = "https://cdn.foodhub.com/items/pizza.jpg"
        )
        String photograph
) {
}
