package br.com.foodhub.infra.web.payload.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload para criação de endereço do usuário")
public record UserAddressRequestPayload(
        @NotBlank
        @Schema(
                description = "CEP do endereço (formatado ou não)",
                example = "81880-390"
        )
        String cep,

        @NotBlank
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
        boolean primary
) {}
