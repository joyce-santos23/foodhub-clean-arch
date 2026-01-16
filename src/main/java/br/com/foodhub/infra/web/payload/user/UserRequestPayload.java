package br.com.foodhub.infra.web.payload.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

@Schema(description = "Payload para criação de usuário")
public record UserRequestPayload(

        @NotBlank
        @Schema(example = "João da Silva")
        String name,

        @NotBlank
        @Email
        @Schema(example = "joao@email.com")
        String email,

        @NotBlank
        @Schema(example = "41999999999")
        String phone,

        @NotBlank
        @Size(min = 6)
        @Schema(example = "senha123")
        String password,

        @Schema(
                description = "CPF do usuário (opcional)",
                example = "123.456.789-09"
        )
        String cpf,

        @NotNull
        @Schema(example = "64f9c0c2a8b7e12f9b0e1234")
        String userTypeId
) {
}
