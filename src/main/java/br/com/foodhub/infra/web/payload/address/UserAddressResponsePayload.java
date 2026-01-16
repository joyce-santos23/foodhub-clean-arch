package br.com.foodhub.infra.web.payload.address;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de endereço do usuário")
public record UserAddressResponsePayload(

        String id,

        @Schema(example = "true")
        boolean primary,

        @Schema(example = "450")
        String number,

        @Schema(example = "Apto 301")
        String complement,

        @Schema(description = "Dados base do endereço")
        AddressBaseResponsePayload address
) {}
