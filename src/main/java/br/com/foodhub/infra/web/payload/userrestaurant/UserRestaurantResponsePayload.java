package br.com.foodhub.infra.web.payload.userrestaurant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do vínculo entre usuário e restaurante")
public record UserRestaurantResponsePayload(
        @Schema(
                description = "ID do restaurante",
                example = "69697bfb4a50530b33363161"
        )
        String restaurantId,

        @Schema(
                description = "ID do tipo de usuário no restaurante",
                example = "69697bfb4a50530b33363161"
        )
        String userTypeId
) {
}
