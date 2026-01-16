package br.com.foodhub.infra.web.payload.restaurant.openinghour;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload para atualização do horário de funcionamento")
public record UpdateOpeningHoursRequestPayload(

        @Schema(
                description = "Horário de abertura",
                example = "08:00"
        )
        String openingTime,

        @Schema(
                description = "Horário de fechamento",
                example = "18:00"
        )
        String closingTime,

        @Schema(
                description = "Indica se o restaurante está fechado no dia",
                example = "false"
        )
        boolean closed
) {}
