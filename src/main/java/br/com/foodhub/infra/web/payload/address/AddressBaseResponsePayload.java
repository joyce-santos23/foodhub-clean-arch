package br.com.foodhub.infra.web.payload.address;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddressBaseResponsePayload(
        @Schema(example = "01001-000")
        String cep,

        @Schema(example = "Praça da Sé")
        String street,

        @Schema(example = "Sé")
        String neighborhood,

        @Schema(example = "São Paulo")
        String city,

        @Schema(example = "SP")
        String state,

        @Schema(example = "Brasil")
        String country
) {}

