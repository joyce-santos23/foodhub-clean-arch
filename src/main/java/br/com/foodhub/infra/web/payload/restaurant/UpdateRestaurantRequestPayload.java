package br.com.foodhub.infra.web.payload.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateRestaurantRequestPayload(

        @Schema(
                description = "Nome empresarial do restaurante",
                example = "Pizzaria Napoli"
        )
        String businessName,

        @Schema(
                description = "Tipo de culinária do restaurante",
                example = "Italiana"
        )
        String cuisineType,

        @Schema(
                description = "Número do endereço do restaurante",
                example = "450"
        )
        String numberStreet,

        @Schema(
                description = "Complemento do endereço (opcional)",
                example = "Loja 2"
        )
        String complement
) {
}
