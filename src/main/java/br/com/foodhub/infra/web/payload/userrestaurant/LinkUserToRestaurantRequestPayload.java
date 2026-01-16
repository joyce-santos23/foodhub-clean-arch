package br.com.foodhub.infra.web.payload.userrestaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LinkUserToRestaurantRequestPayload(
        @NotBlank
        @Schema(example = "69697bfb4a50530b33363161")
        String restaurantId,

        @NotBlank
        @Schema(example = "69697bfb4a50530b33363161")
        String userTypeId
) {
}
