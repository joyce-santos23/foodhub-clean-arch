package br.com.foodhub.infra.web.payload.address;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserAddressRequestPayload(
        @Schema(
                description = "Número do endereço",
                example = "123"
        )
        String number,

        @Schema(
                description = "Complemento do endereço (opcional)",
                example = "Apto 301"
        )
        String complement,

        @Schema(
                description = "Define se é o endereço principal",
                example = "true"
        )
        Boolean primary
) {
}
