package br.com.foodhub.infra.web.payload.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload para atualização de usuário")
public record UpdateUserPayload(

        @Schema(example = "João Atualizado")
        String name,

        @Email
        @Schema(example = "joao@email.com")
        String email,

        @Schema(example = "41988888888")
        String phone
) {}
