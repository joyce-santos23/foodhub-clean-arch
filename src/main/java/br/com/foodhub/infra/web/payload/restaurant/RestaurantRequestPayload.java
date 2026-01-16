package br.com.foodhub.infra.web.payload.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload para criação de restaurante")
public record RestaurantRequestPayload(

        @NotBlank
        @Schema(
                description = "Nome empresarial do restaurante",
                example = "Pizzaria Napoli"
        )
        String businessName,

        @NotBlank
        @Schema(
                description = "CNPJ do restaurante",
                example = "12.345.678/0001-90"
        )
        String cnpj,

        @NotBlank
        @Schema(
                description = "Tipo de culinária do restaurante",
                example = "Italiana"
        )
        String cuisineType,

        @NotBlank
        @Schema(
                description = "CEP do restaurante",
                example = "81883-400"
        )
        String cep,

        @NotBlank
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
) {}
