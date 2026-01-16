package br.com.foodhub.infra.web.payload.menu;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de menu")
public record MenuResponsePayload(

        @Schema(example = "69697bfb4a50530b33363161")
        String id,

        @Schema(example = "Cardápio Principal")
        String name
) {}
