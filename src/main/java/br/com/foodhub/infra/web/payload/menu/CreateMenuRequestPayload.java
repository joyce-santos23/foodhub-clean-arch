package br.com.foodhub.infra.web.payload.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload para criação de menu do restaurante")
public record CreateMenuRequestPayload(

        @NotBlank
        @Schema(
                description = "Nome do menu",
                example = "Cardápio Principal"
        )
        String name
) {}
